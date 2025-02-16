package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.service.cart.GetCartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GetCartItemServiceImpl implements GetCartItemService {

    private final CartItemRepository cartItemRepository;

    public GetCartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public List<CartItem> getAllByActiveCartAndCartId(Long cartId, boolean isActive) {
        List<CartItem> activeCartItems = cartItemRepository.findAllByActiveCartAndCartId(cartId, true);
        if (activeCartItems.isEmpty()){
            throw new DataNotFoundException("Item not found !");
        }
        return activeCartItems;
    }

    @Override
    @Transactional
    public Optional<CartItem> getByActiveCartAndCartIdAndProductId(Long cartId, Long productId, boolean isActive) {
        Optional<CartItem> currentCartItem = cartItemRepository.findByActiveCartAndCartIdAndProductId(cartId, productId, true);
        if (currentCartItem.isEmpty()){
            throw new DataNotFoundException("Cart item not found !");
        }
        return currentCartItem;
    }
}
