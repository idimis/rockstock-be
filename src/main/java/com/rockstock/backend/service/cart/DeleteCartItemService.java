package com.rockstock.backend.service.cart;

public interface DeleteCartItemService {
    void removeCartItem(Long cartItemId);
    void deleteByCartId(Long cartId);
}
