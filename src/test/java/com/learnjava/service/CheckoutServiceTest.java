package com.learnjava.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;

class CheckoutServiceTest {

	private PriceValidatorService priceValidatorService = new PriceValidatorService();
	private CheckoutService checkoutService = new CheckoutService(priceValidatorService);

	@Test
	void checkout_6_items() {

		//given
		Cart cart = DataSet.createCart(25);

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
