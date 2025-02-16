package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartItemRequestDTO;

public interface CreateCartItemService {
    CartItem addToCart(CreateCartItemRequestDTO req);
}
