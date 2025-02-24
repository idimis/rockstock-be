package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.UnauthorizedException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.infrastructure.cart.dto.GetCartItemResponseDTO;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.cart.CreateCartService;
import com.rockstock.backend.service.cart.GetCartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCartItemServiceImpl implements GetCartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CreateCartService createCartService;

    @Override
    @Transactional
    public List<GetCartItemResponseDTO> getAllByActiveCartId() {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);
        if (existingActiveCart == null) {
            existingActiveCart = createCartService.createCart();
        }

        List<CartItem> activeCartItems = cartItemRepository.findAllByActiveCartId(existingActiveCart.getId());
        if (activeCartItems.isEmpty()){
            throw new DataNotFoundException("Item not found !");
        }

        return activeCartItems.stream()
                .map(GetCartItemResponseDTO::new)
                .toList();
    }

    @Override
    @Transactional
    public Optional<GetCartItemResponseDTO> getByActiveCartIdAndId(Long cartItemId) {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);
        if (existingActiveCart == null) {
            existingActiveCart = createCartService.createCart();
        }

        Optional<CartItem> currentCartItem = cartItemRepository.findByActiveCartIdAndProductId(existingActiveCart.getId(), cartItemId);
        if (currentCartItem.isEmpty()){
            throw new DataNotFoundException("Cart item not found !");
        }

        return currentCartItem.map(GetCartItemResponseDTO::new);
    }

    @Override
    @Transactional
    public Optional<GetCartItemResponseDTO> getByActiveCartIdAndProductId(Long productId) {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);
        if (existingActiveCart == null) {
            existingActiveCart = createCartService.createCart();
        }

        Optional<CartItem> currentCartItem = cartItemRepository.findByActiveCartIdAndProductId(existingActiveCart.getId(), productId);
        if (currentCartItem.isEmpty()){
            throw new DataNotFoundException("Cart item not found !");
        }

        return currentCartItem.map(GetCartItemResponseDTO::new);
    }

    @Override
    @Transactional
    public Optional<GetCartItemResponseDTO> getByActiveCartIdAndProductName(String name) {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);
        if (existingActiveCart == null) {
            existingActiveCart = createCartService.createCart();
        }

        Optional<CartItem> currentCartItem = cartItemRepository.findByActiveCartIdAndProductName(existingActiveCart.getId(), name);
        if (currentCartItem.isEmpty()){
            throw new DataNotFoundException("Cart item not found !");
        }

        return currentCartItem.map(GetCartItemResponseDTO::new);
    }
}
