package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.OutOfStockException;
import com.rockstock.backend.common.exceptions.StockLimitException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.cart.dto.UpdateCartItemRequestDTO;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.cart.DeleteCartItemService;
import com.rockstock.backend.service.cart.UpdateCartItemService;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.iap.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateCartItemServiceImpl implements UpdateCartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DeleteCartItemService deleteCartItemService;

    private Product getValidProduct(Long productId) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));

        if (product.getTotalStock().compareTo(BigDecimal.ZERO) == 0) {
            throw new OutOfStockException("Product is out of stock!");
        }

        return product;
    }

    @Transactional
    public CartItem addCartItem(Long productId) {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);

        Product product = getValidProduct(productId);

        Optional<CartItem> currentCartItem = cartItemRepository.findByActiveCartIdAndProductId(existingActiveCart.getId(),product.getId());
        if (currentCartItem.isEmpty()) {
            throw new DataNotFoundException("Cart item not found !");
        }

        CartItem cartItem = currentCartItem.get();

        if (cartItem.getQuantity().compareTo(product.getTotalStock()) >= 0) {
            throw new StockLimitException("Hit stock limit !");
        }

        cartItem.setQuantity(cartItem.getQuantity().add(BigDecimal.ONE));
        cartItem.setTotalAmount(cartItem.getQuantity().multiply(product.getPrice()));

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public CartItem subtractCartItem(Long productId) {
        Long userId = Claims.getUserIdFromJwt();

        Cart existingActiveCart = cartRepository.findActiveCartByUserId(userId);

        Product product = getValidProduct(productId);

        Optional<CartItem> currentCartItem = cartItemRepository.findByActiveCartIdAndProductId(existingActiveCart.getId(),product.getId());
        if (currentCartItem.isEmpty()) {
            throw new DataNotFoundException("Cart item not found !");
        }

        CartItem cartItem = currentCartItem.get();

        if (cartItem.getQuantity().compareTo(BigDecimal.ONE) == 0) {
            deleteCartItemService.removeCartItem(cartItem.getId());
            return null;
        } else {
            cartItem.setQuantity(cartItem.getQuantity().subtract(BigDecimal.ONE));
            cartItem.setTotalAmount(cartItem.getQuantity().multiply(product.getPrice()));
            return cartItemRepository.save(cartItem);
        }
    }
}
