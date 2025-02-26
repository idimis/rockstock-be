package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.OutOfStockException;
import com.rockstock.backend.common.exceptions.StockLimitException;
import com.rockstock.backend.common.exceptions.UnauthorizedException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartItemRequestDTO;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.cart.CreateCartItemService;
import com.rockstock.backend.service.cart.CreateCartService;
import com.rockstock.backend.service.cart.UpdateCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateCartItemServiceImpl implements CreateCartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CreateCartService createCartService;
    private final UpdateCartService updateCartService;

    private Product getValidProduct(Long productId) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found !"));

        if (product.getTotalStock().compareTo(BigDecimal.ZERO) == 0) {
            throw new OutOfStockException("Product is out of stock !");
        }

        return product;
    }

    @Override
    @Transactional
    public CartItem addToCart(CreateCartItemRequestDTO req) {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);
        if (existingActiveCart == null) {
            existingActiveCart = createCartService.createCart();
        }

        if (!existingActiveCart.getIsActive()) {
            throw new RuntimeException("Cannot add item to inactive cart !");
        }

        Product product = getValidProduct(req.getProductId());

        Optional<CartItem> existingItem = cartItemRepository.findByActiveCartIdAndProductId(existingActiveCart.getId(), product.getId());
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            if (BigDecimal.valueOf(cartItem.getQuantity()).compareTo(product.getTotalStock()) >= 0) {
                throw new StockLimitException("Hit stock limit !");
            }

            cartItem.setQuantity(cartItem.getQuantity() + req.getQuantity());
            cartItem.setTotalAmount(BigDecimal.valueOf(cartItem.getQuantity()).multiply(product.getPrice()));

            updateCartService.updateItemQuantity();

            return cartItemRepository.save(cartItem);
        } else {
            CartItem newItem = req.toEntity(product);

            newItem.setCart(existingActiveCart);
            newItem.setQuantity(req.getQuantity());
            newItem.setTotalAmount(BigDecimal.valueOf(req.getQuantity()).multiply(product.getPrice()));

            updateCartService.updateItemQuantity();

            return cartItemRepository.save(newItem);
        }
    }
}
