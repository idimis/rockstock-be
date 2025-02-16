package com.rockstock.backend.service.cart;

import com.rockstock.backend.entity.cart.CartItem;

import java.util.List;
import java.util.Optional;

public interface GetCartItemService {
    List<CartItem> getAllByActiveCartAndCartId(Long cartId, boolean isActive);
    Optional<CartItem> getByActiveCartAndCartIdAndProductId(Long cartId, Long productId, boolean isActive);
}
