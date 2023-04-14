package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.learnjava.domain.Inventory;
import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.domain.Review;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import com.learnjava.util.LoggerUtil;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = cfProductInfo
          .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
          .join(); // block thread

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
          .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
          .thenApply(productInfo -> {
            productInfo.setProductOptions(updateInventory_approach2(productInfo));
            return productInfo;
          });

        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = cfProductInfo
          .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
          .join(); // block thread

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> cfReview = CompletableFuture
          .supplyAsync(() -> reviewService.retrieveReviews(productId))
          .exceptionally(throwable -> {
              LoggerUtil.log("Exception in review service" + throwable.getMessage());
              return Review.builder()
                .noOfReviews(0)
                .overallRating(0.0d)
                .build();
          });

        return cfProductInfo
          .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review));
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        List<ProductOption> productOptionList = productInfo.getProductOptions()
          .parallelStream()
          .map(productOption -> {
              Inventory inventory = inventoryService.addInventory(productOption);
              productOption.setInventory(inventory);
              return productOption;
          })
          .collect(Collectors.toList());

        return productOptionList;
    }

    private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
          .stream()
          .map(productOption -> {
              return CompletableFuture.supplyAsync(() -> inventoryService.addInventory(productOption))
                .thenApply(inventory -> {
                    productOption.setInventory(inventory);
                    return productOption;
                });
          })
          .collect(Collectors.toList());

        return productOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
