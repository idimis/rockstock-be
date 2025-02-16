package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.Cart;

import java.util.List;
import java.util.Optional;

public interface GetCartService {
    List<Cart> getAllByUserId(Long userId);
    Optional<Cart> getActiveCartByUserId(Long userId, boolean isActive);
}
