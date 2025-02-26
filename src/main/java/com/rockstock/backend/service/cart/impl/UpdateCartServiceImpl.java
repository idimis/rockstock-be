package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.cart.UpdateCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateCartServiceImpl implements UpdateCartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void updateItemQuantity() {
        Long userId = Claims.getUserIdFromJwt();

        Cart cart = cartRepository.findActiveCartByUserId(userId);
        if (cart == null) {
            throw new DataNotFoundException("Cart not found ");
        }

        List<CartItem> cartItems = cartItemRepository.findAllByActiveCartId(cart.getId());

        Integer itemQuantity = cartItems.stream().map(CartItem::getQuantity).reduce(0, Integer::sum);
        cart.setItemQuantity(itemQuantity);

        cartRepository.save(cart);
    }
}
