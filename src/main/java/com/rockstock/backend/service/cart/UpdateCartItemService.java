package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.infrastructure.cart.dto.UpdateCartItemRequestDTO;

public interface UpdateCartItemService {
    CartItem addCartItem(Long productId);
    CartItem subtractCartItem(Long productId);
}
