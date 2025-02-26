package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.CartItem;

public interface UpdateCartItemService {
    CartItem addCartItem(Long productId);
    CartItem subtractCartItem(Long productId);
}
