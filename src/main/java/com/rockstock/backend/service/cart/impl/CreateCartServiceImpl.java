package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartRequestDTO;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.cart.CreateCartService;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CreateCartServiceImpl implements CreateCartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CreateCartServiceImpl(
            CartRepository cartRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Cart createCart(CreateCartRequestDTO req) {
        Optional<Cart> checkActiveCart = cartRepository.findActiveCartByUserId(req.getUserId(), true );
        if (checkActiveCart.isPresent()) {
            throw new DuplicateRequestException("Active cart is already exist !");
        }

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart newCart = new Cart();

        newCart.setUser(user);

        return cartRepository.save(newCart);
    }
}
