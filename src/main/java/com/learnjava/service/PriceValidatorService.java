package com.learnjava.service;


import com.learnjava.domain.checkout.CartItem;
import com.learnjava.util.LoggerUtil;

import static com.learnjava.util.CommonUtil.delay;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        LoggerUtil.log("is cart item "+ cartId);
        delay(500);
        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;
        }
        return false;
    }
}
