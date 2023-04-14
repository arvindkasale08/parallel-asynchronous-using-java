package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUsingCompletableFutureExceptionTest {

	@InjectMocks
	private ProductServiceUsingCompletableFuture pscf = Mockito.mock(ProductServiceUsingCompletableFuture.class);
	@Mock
	private ProductInfoService productInfoService;
	@Mock
	private ReviewService reviewService;
	@Mock
	private InventoryService inventoryService;

	@Test
	void testRetrieveProductDetails_approach2() {

		String productId = "ABC123";

		Mockito.when(productInfoService.retrieveProductInfo(Mockito.any())).thenCallRealMethod();
		Mockito.when(reviewService.retrieveReviews(Mockito.any())).thenThrow(new RuntimeException("Review service failed"));
		Mockito.when(inventoryService.addInventory(Mockito.any())).thenCallRealMethod();

		// when
		pscf.retrieveProductDetails_approach2(productId)
			.thenAccept(product -> {
				assertNotNull(product);
				assertTrue(product.getProductInfo().getProductOptions().size() > 0);
				assertNotNull(product.getReview());
				assertEquals(product.getReview().getNoOfReviews(), 0);
				assertNotNull(product.getReview());
			})
			.join();
	}
}
