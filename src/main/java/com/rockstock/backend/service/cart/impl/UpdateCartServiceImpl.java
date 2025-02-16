package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.infrastructure.cart.dto.UpdateCartRequestDTO;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.cart.UpdateCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateCartServiceImpl implements UpdateCartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public UpdateCartServiceImpl(
            CartRepository cartRepository,
            UserRepository userRepository,
            CartItemRepository cartItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Cart updateCart(Long userId, Long cartId, UpdateCartRequestDTO req) {
        Optional<Cart> existingActiveCart = cartRepository.findActiveCartByUserId(userId, true);
        if (existingActiveCart.isEmpty()) {
            throw new RuntimeException("Active cart not found !");
        }

        Cart currentActiveCart = existingActiveCart.get();
        currentActiveCart.setIsActive(false);

        return cartRepository.save(currentActiveCart);
    }
}
