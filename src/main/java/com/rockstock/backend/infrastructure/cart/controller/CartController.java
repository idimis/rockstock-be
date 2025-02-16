package com.rockstock.backend.infrastructure.cart.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartItemRequestDTO;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartRequestDTO;
import com.rockstock.backend.infrastructure.cart.dto.UpdateCartItemRequestDTO;
import com.rockstock.backend.infrastructure.cart.dto.UpdateCartRequestDTO;
import com.rockstock.backend.service.cart.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CreateCartService createCartService;
    private final CreateCartItemService createCartItemService;
    private final GetCartService getCartService;
    private final GetCartItemService getCartItemService;
    private final UpdateCartService updateCartService;
    private final UpdateCartItemService updateCartItemService;
    private final DeleteCartItemService deleteCartItemService;

    public CartController(
            CreateCartService createCartService,
            CreateCartItemService createCartItemService,
            GetCartService getCartService,
            GetCartItemService getCartItemService,
            UpdateCartService updateCartService,
            UpdateCartItemService updateCartItemService,
            DeleteCartItemService deleteCartItemService
    ) {
        this.createCartService = createCartService;
        this.createCartItemService = createCartItemService;
        this.getCartService = getCartService;
        this.getCartItemService = getCartItemService;
        this.updateCartService = updateCartService;
        this.updateCartItemService = updateCartItemService;
        this.deleteCartItemService = deleteCartItemService;
    }

    // Create
    @PostMapping
    public ResponseEntity<?> createCart(@Valid @RequestBody CreateCartRequestDTO req) {
        return ApiResponse.success(HttpStatus.OK.value(), "Create cart success!", createCartService.createCart(req));
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CreateCartItemRequestDTO req) {
        return ApiResponse.success(HttpStatus.OK.value(), "Add to cart success!", createCartItemService.addToCart(req));
    }

    // Read / Get
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long userId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all user's carts success!", getCartService.getAllByUserId(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<?> getActiveCartByUserId(@PathVariable Long userId, @PathVariable boolean isActive) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get user's active cart success!", getCartService.getActiveCartByUserId(userId, true));
    }

    @GetMapping("/{cartId}/active")
    public ResponseEntity<?> getAllByActiveCartAndCartId(@PathVariable Long cartId, @PathVariable boolean isActive) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all items from active cart success!", getCartItemService.getAllByActiveCartAndCartId(cartId, true));
    }

    @GetMapping("/{cartId}/active/product/{productId}")
    public ResponseEntity<?> getByActiveCartAndCartIdAndProductId(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable boolean isActive) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get item from active cart success!", getCartItemService.getByActiveCartAndCartIdAndProductId(cartId, productId, true));
    }

    // Update
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable Long userId, @PathVariable Long cartId, @RequestBody UpdateCartRequestDTO req) {
        return ApiResponse.success(HttpStatus.OK.value(), "Inactive an active cart success!", updateCartService.updateCart(userId, cartId, req));
    }

    @PutMapping("/{cartId}/{cartItemId}/add")
    public ResponseEntity<?> addCartItem(
            @PathVariable Long cartItemId,
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestBody UpdateCartItemRequestDTO req
    ) {
        return ApiResponse.success(HttpStatus.OK.value(), "Add item to cart success!", updateCartItemService.addCartItem(cartItemId, cartId, productId, req));
    }

    @PutMapping("/{cartId}/{cartItemId}/subtract")
    public ResponseEntity<?> subtractCartItem(
            @PathVariable Long cartItemId,
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestBody UpdateCartItemRequestDTO req) {
        return ApiResponse.success("Subtract item to cart success!");
    }

    //Delete
    @DeleteMapping("/{cartId}/{cartItemId}/remove")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        deleteCartItemService.deleteCartItem(cartId, cartItemId);
        return ApiResponse.success("Item removed successfully!");
    }
}
