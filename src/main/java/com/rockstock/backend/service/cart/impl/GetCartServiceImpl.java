package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.cart.CreateCartService;
import com.rockstock.backend.service.cart.GetCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCartServiceImpl implements GetCartService {

    private final CartRepository cartRepository;
    private final CreateCartService createCartService;

    @Override
    @Transactional
    public Cart getActiveCartByUserId() {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);
        if (existingActiveCart == null) {
            existingActiveCart = createCartService.createCart();
        }
        return existingActiveCart;
    }
}
