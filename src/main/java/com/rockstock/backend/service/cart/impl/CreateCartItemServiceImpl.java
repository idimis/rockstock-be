package com.rockstock.backend.service.cart.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.OutOfStockException;
import com.rockstock.backend.common.exceptions.StockLimitException;
import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartItemRequestDTO;
import com.rockstock.backend.infrastructure.cart.repository.CartItemRepository;
import com.rockstock.backend.infrastructure.cart.repository.CartRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.service.cart.CreateCartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CreateCartItemServiceImpl implements CreateCartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CreateCartItemServiceImpl(
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            CartRepository cartRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

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
        Cart cart = cartRepository.findById(req.getCartId())
                .orElseThrow(() -> new DataNotFoundException("Cart not found"));

        if (!cart.getIsActive()) {
            throw new RuntimeException("Cannot add item to inactive cart !");
        }

        Product product = getValidProduct(req.getProductId());

        Optional<CartItem> existingItem = cartItemRepository.findByActiveCartAndCartIdAndProductId(req.getCartId(), req.getProductId(), true);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            if (cartItem.getQuantity().compareTo(product.getTotalStock()) >= 0) {
                throw new StockLimitException("Stock limit hit !");
            }

            cartItem.setQuantity(cartItem.getQuantity().add(BigDecimal.ONE));
            cartItem.setTotalAmount(cartItem.getQuantity().multiply(product.getPrice()));
            return cartItemRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(BigDecimal.ONE);
            newItem.setTotalAmount(product.getPrice());
            return cartItemRepository.save(newItem);
        }
    }
}
