package com.rockstock.backend.infrastructure.product.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.product.dto.CreateProductRequestDTO;
import com.rockstock.backend.infrastructure.product.dto.CreateProductResponseDTO;
import com.rockstock.backend.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Create
    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResponseDTO>> createProduct(@Valid @RequestBody CreateProductRequestDTO createProductRequestDTO) {
        CreateProductResponseDTO response = productService.createProduct(createProductRequestDTO);
        return ApiResponse.success("Product created successfully", response);
    }

    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateProductResponseDTO>> updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductRequestDTO createProductRequestDTO) {
        CreateProductResponseDTO response = productService.updateProduct(id, createProductRequestDTO);
        return ApiResponse.success("Product updated successfully", response);
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateProductResponseDTO>> getProductById(@PathVariable Long id) {
        CreateProductResponseDTO response = productService.getProductById(id);
        return ApiResponse.success("Product found", response);
    }

    // Get All Products
    @GetMapping
    public Page<Product> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sortField", defaultValue = "productName") String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection) {

        // Call the service layer to get the paginated and filtered products
        return productService.getAllProducts(page, size, name, category, sortField, sortDirection);
    }

    // Soft Delete Product
    @DeleteMapping("/{id}/soft")
    public ResponseEntity<ApiResponse<String>> softDeleteProduct(@PathVariable Long id) {
        productService.softDeleteProduct(id);
        return ApiResponse.success("Product soft deleted successfully");
    }

    // Hard Delete Product
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<ApiResponse<String>> hardDeleteProduct(@PathVariable Long id) {
        productService.hardDeleteProduct(id);
        return ApiResponse.success("Product hard deleted successfully");
    }
}