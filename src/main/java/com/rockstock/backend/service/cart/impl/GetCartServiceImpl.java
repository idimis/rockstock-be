package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.cart.GetCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GetCartServiceImpl implements GetCartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public GetCartServiceImpl(
            CartRepository cartRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<Cart> getAllByUserId(Long userId) {
        List<Cart> userCarts = cartRepository.findByUserId(userId);
        if (userCarts.isEmpty()){
            throw new DataNotFoundException("Cart not found !");
        }
        return userCarts;
    }

    @Override
    @Transactional
    public Optional<Cart> getActiveCartByUserId(Long userId, boolean isActive) {
        Optional<Cart> activeCart = cartRepository.findActiveCartByUserId(userId, true);
        if (activeCart.isEmpty()){
            throw new DataNotFoundException("Active cart not found !");
        }
        return activeCart;
    }
}
