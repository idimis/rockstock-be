package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.OutOfStockException;
import com.rockstock.backend.common.exceptions.StockLimitException;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.cart.dto.UpdateCartItemRequestDTO;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.service.cart.UpdateCartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UpdateCartItemServiceImpl implements UpdateCartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public UpdateCartItemServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    private Product getValidProduct(Long productId) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));

        if (product.getTotalStock().compareTo(BigDecimal.ZERO) == 0) {
            throw new OutOfStockException("Product is out of stock!");
        }

        return product;
    }

    @Transactional
    public CartItem addCartItem(Long cartItemId, Long cartId, Long productId, UpdateCartItemRequestDTO req) {
        Product product = getValidProduct(productId);

        CartItem currentCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new DataNotFoundException("Cart item not found!"));

        if (currentCartItem.getQuantity().compareTo(product.getTotalStock()) >= 0) {
            throw new StockLimitException("Stock limit hit!");
        }

        currentCartItem.setQuantity(currentCartItem.getQuantity().add(BigDecimal.ONE));
        currentCartItem.setTotalAmount(currentCartItem.getQuantity().multiply(product.getPrice()));

        return cartItemRepository.save(currentCartItem);
    }

    @Transactional
    public void subtractCartItem(Long cartItemId, Long cartId, Long productId, UpdateCartItemRequestDTO req) {
        Product product = getValidProduct(productId);

        CartItem currentCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new DataNotFoundException("Cart item not found!"));

        if (currentCartItem.getQuantity().compareTo(BigDecimal.ONE) == 0) {
            cartItemRepository.delete(currentCartItem);
        } else {
            currentCartItem.setQuantity(currentCartItem.getQuantity().subtract(BigDecimal.ONE));
            currentCartItem.setTotalAmount(currentCartItem.getQuantity().multiply(product.getPrice()));
            cartItemRepository.save(currentCartItem);
        }
    }
}
