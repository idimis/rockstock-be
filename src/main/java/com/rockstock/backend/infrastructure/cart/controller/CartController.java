package com.rockstock.backend.infrastructure.cart.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.cart.dto.CreateCartItemRequestDTO;
import com.rockstock.backend.service.cart.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CreateCartService createCartService;
    private final CreateCartItemService createCartItemService;
    private final GetCartService getCartService;
    private final GetCartItemService getCartItemService;
    private final UpdateCartItemService updateCartItemService;
    private final DeleteCartItemService deleteCartItemService;

    // Create
    @PostMapping
    public ResponseEntity<?> createCart() {
        return ApiResponse.success(HttpStatus.OK.value(), "Create cart success!", createCartService.createCart());
    }

    @PostMapping("/item")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CreateCartItemRequestDTO req) {
        return ApiResponse.success(HttpStatus.OK.value(), "Add to cart success!", createCartItemService.addToCart(req));
    }

    // Read / Get
    @GetMapping("/active")
    public ResponseEntity<?> getActiveCartByUserId() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get user's active cart success!", getCartService.getActiveCartByUserId());
    }

    @GetMapping("/active/products")
    public ResponseEntity<?> getAllByActiveCartId() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all items from active cart success!", getCartItemService.getAllByActiveCartId());
    }

    @GetMapping("/active/items/item")
    public ResponseEntity<?> getByActiveCartIdAndId(@RequestParam Long cartItemId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get item from active cart success!", getCartItemService.getByActiveCartIdAndId(cartItemId));
    }

    @GetMapping("/active/products/product")
    public ResponseEntity<?> getByActiveCartIdAndProductId(@RequestParam Long productId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get item from active cart success!", getCartItemService.getByActiveCartIdAndProductId(productId));
    }

    @GetMapping("/active/products/product-name")
    public ResponseEntity<?> getByActiveCartIdAndProductName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get item from active cart success!", getCartItemService.getByActiveCartIdAndProductName(name));
    }

    // Update
    @PutMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestParam Long productId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Add item to cart success!", updateCartItemService.addCartItem(productId));
    }

    @PutMapping("/subtract")
    public ResponseEntity<?> subtractCartItem(@RequestParam Long productId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Subtract item to cart success!", updateCartItemService.subtractCartItem(productId));
    }

    //Delete
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeCartItem(@RequestParam Long cartItemId) {
        deleteCartItemService.removeCartItem(cartItemId);
        return ApiResponse.success("Item removed successfully!");
    }

    @DeleteMapping("/remove-all")
    public ResponseEntity<?> deleteByCartId(@RequestParam Long cartId) {
        deleteCartItemService.deleteByCartId(cartId);
        return ApiResponse.success("All user cart items removed successfully!");
    }
}
