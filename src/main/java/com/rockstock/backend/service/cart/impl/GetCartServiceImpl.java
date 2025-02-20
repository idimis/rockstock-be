package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.service.cart.GetCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCartServiceImpl implements GetCartService {

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public Cart getActiveCartByUserId(Long userId) {
        Cart activeCart = cartRepository.findActiveCartByUserId(userId);
        if (activeCart == null){
            throw new DataNotFoundException("Active cart not found !");
        }
        return activeCart;
    }
}
