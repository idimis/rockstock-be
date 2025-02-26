package com.rockstock.backend.infrastructure.product.controller;

import com.rockstock.backend.infrastructure.product.dto.*;
import com.rockstock.backend.service.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final GetProductService getProductService;

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<GetProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(getProductService.getProductById(id));
    }

    // ✅ Get all products with filters, sorting, and pagination
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<GetAllProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(getProductService.getAllProducts(page, size, name, category, sortField, sortDirection));
    }
}