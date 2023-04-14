package com.learnjava.service;

import java.util.List;
import java.util.stream.Collectors;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.CommonUtil;

public class CheckoutService {

	private PriceValidatorService priceValidatorService;

	public CheckoutService(PriceValidatorService priceValidatorService) {
		this.priceValidatorService = priceValidatorService;
	}

	public CheckoutResponse checkout(Cart cart) {
		CommonUtil.startTimer();
		List<CartItem> priceValidationList = cart.getCartItemList()
			.parallelStream()
			.map(cartItem -> {
				boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
				cartItem.setExpired(isPriceInvalid);
				return cartItem;
			})
			.filter(CartItem::isExpired)
			.collect(Collectors.toList());

		CommonUtil.timeTaken();
		if (priceValidationList.size() > 0) {
			return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
		}

		return new CheckoutResponse(CheckoutStatus.SUCCESS);
	}
}
