package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.infrastructure.cart.dto.UpdateCartRequestDTO;

public interface UpdateCartService {
    Cart updateCart(Long userId, Long cartId, UpdateCartRequestDTO req);
}
