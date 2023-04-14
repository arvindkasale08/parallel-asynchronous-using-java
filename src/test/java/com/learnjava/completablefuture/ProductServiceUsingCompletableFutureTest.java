package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

class ProductServiceUsingCompletableFutureTest {

	private ProductInfoService pis = new ProductInfoService();
	private ReviewService rs = new ReviewService();
	private InventoryService is = new InventoryService();
	ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(pis, rs, is);

	@Test
	void retrieveProductDetails() {
		// given
		String productId = "ABC123";

		// when
		Product product = pscf.retrieveProductDetails(productId);

		// then
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		assertNotNull(product.getReview());
	}

	@Test
	void retrieveProductDetails_approach2() {
		// given
		String productId = "ABC123";

		// when
		pscf.retrieveProductDetails_approach2(productId)
			.thenAccept(product -> {
				assertNotNull(product);
				assertTrue(product.getProductInfo().getProductOptions().size() > 0);
				assertNotNull(product.getReview());
			})
			.join();
	}

	@Test
	void retrieveProductDetailsWithInventory() {

		// given
		String productId = "ABC123";

		// when
		Product product = pscf.retrieveProductDetailsWithInventory(productId);

		// then
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		assertNotNull(product.getReview());
		assertNotNull(product.getProductInfo().getProductOptions().get(0).getInventory());
		assertEquals(product.getProductInfo().getProductOptions().get(0).getInventory().getCount(), 2);
	}


}
