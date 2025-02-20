package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.CartItem;

import java.util.List;
import java.util.Optional;

public interface GetCartItemService {
    List<CartItem> getAllByActiveCartId();
    Optional<CartItem> getByActiveCartIdAndId(Long cartItemId);
    Optional<CartItem> getByActiveCartIdAndProductId(Long productId);
    Optional<CartItem> getByActiveCartIdAndProductName(String name);
}
