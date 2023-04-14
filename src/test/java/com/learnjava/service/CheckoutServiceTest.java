package com.learnjava.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;

class CheckoutServiceTest {

	private PriceValidatorService priceValidatorService = new PriceValidatorService();
	private CheckoutService checkoutService = new CheckoutService(priceValidatorService);

	@Test
	void checkout_6_items() {

		CommonUtil.stopWatch.reset();

		//given
		Cart cart = DataSet.createCart(6);

		//when
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

		//then
		assertEquals(checkoutResponse.getCheckoutStatus(), CheckoutStatus.SUCCESS);
		assertTrue(checkoutResponse.getFinalRate() > 0);
	}

	@Test
	void checkout_15_items() {

		CommonUtil.stopWatch.reset();
		//given
		Cart cart = DataSet.createCart(15);

		//when
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

		//then
		assertEquals(checkoutResponse.getCheckoutStatus(), CheckoutStatus.FAILURE);
	}

	@Test
	void no_of_cores() {
		System.out.println("No of cores is "+ Runtime.getRuntime().availableProcessors());
	}
}
