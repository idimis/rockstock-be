package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.Cart;

import java.util.List;
import java.util.Optional;

public interface GetCartService {
    Cart getActiveCartByUserId(Long userId);
}
