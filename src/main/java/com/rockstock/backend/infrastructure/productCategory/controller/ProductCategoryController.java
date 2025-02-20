package com.rockstock.backend.infrastructure.productCategory.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.productCategory.dto.*;
import com.rockstock.backend.service.product.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

//    @PreAuthorize("hasRole('SUPER ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreateProductCategoryResponseDTO>> createProductCategory(@Valid @RequestBody CreateProductCategoryRequestDTO createProductCategoryRequestDTO) {
//            Long sellerId = Claims.getUserIdFromJwt();
       CreateProductCategoryResponseDTO createProductCategoryResponseDTO = productCategoryService.createProductCategory(createProductCategoryRequestDTO);
       return ApiResponse.success("Create new category success", createProductCategoryResponseDTO);
    }

//    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CreateProductCategoryResponseDTO>> updateProduct(
            @PathVariable Long categoryId,
            @RequestBody @Valid UpdateProductCategoryRequestDTO updateCategoryDTO) {
        updateCategoryDTO.setCategoryId(categoryId);

        CreateProductCategoryResponseDTO updatedCategory = productCategoryService.updateProductCategory(updateCategoryDTO);
        return ApiResponse.success("Update category success", updatedCategory );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductCategory(@PathVariable Long categoryId) {
        productCategoryService.deleteProductCategory(categoryId);
        return ApiResponse.success("Delete product success", null);
    }

    @GetMapping public ResponseEntity<ApiResponse<List<HomeProductCategoryDTO>>> getAllCategories() {
        List<HomeProductCategoryDTO> categories = productCategoryService.getAllCategories();
        return ApiResponse.success("Fetched all categories", categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<GetProductCategoryResponseDTO>> getCategoryById(
            @PathVariable Long categoryId) {
        GetProductCategoryResponseDTO category = productCategoryService.getProductCategoryById(categoryId);
        return ApiResponse.success("Product found", category);
    }
}