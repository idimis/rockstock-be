package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartRequestDTO;

public interface CreateCartService {
    Cart createCart(CreateCartRequestDTO req);
}
