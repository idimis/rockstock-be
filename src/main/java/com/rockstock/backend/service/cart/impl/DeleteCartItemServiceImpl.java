package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.UnauthorizedException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.cart.DeleteCartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCartItemServiceImpl implements DeleteCartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public void removeCartItem(Long cartItemId) {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);

        CartItem deletedCartItem = cartItemRepository.findByIdAndCartId(cartItemId, existingActiveCart.getId())
                .orElseThrow(() -> new DataNotFoundException("Cart item not found!"));

        cartItemRepository.delete(deletedCartItem);
    }
}
