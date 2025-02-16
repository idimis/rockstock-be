package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.service.cart.DeleteCartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteCartItemServiceImpl implements DeleteCartItemService {

    private final CartItemRepository cartItemRepository;

    public DeleteCartItemServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartId, Long cartItemId) {
        CartItem deletedCartItem = cartItemRepository.findByIdAndCartId(cartId, cartItemId)
                .orElseThrow(() -> new DataNotFoundException("Cart item not found!"));

        cartItemRepository.delete(deletedCartItem);
    }
}
