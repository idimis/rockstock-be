package com.rockstock.backend.infrastructure.system.productCategory.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.system.productCategory.dto.CreateProductCategoryRequestDTO;
import com.rockstock.backend.infrastructure.system.productCategory.dto.CreateProductCategoryResponseDTO;
import com.rockstock.backend.infrastructure.system.productCategory.dto.UpdateProductCategoryRequestDTO;
import com.rockstock.backend.usecase.product.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
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
       return ApiResponse.successfulResponse("Create new category success", createProductCategoryResponseDTO);
    }

//    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CreateProductCategoryResponseDTO>> updateProduct(
            @PathVariable Long categoryId,
            @RequestBody @Valid UpdateProductCategoryRequestDTO updateCategoryDTO) {
        updateCategoryDTO.setCategoryId(categoryId);

        CreateProductCategoryResponseDTO updatedCategory = productCategoryService.updateProductCategory(updateCategoryDTO);
        return ApiResponse.successfulResponse("Update category success", updatedCategory );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductCategory(@PathVariable Long categoryId) {
        productCategoryService.deleteProductCategory(categoryId);
        return ApiResponse.successfulResponse("Delete product success", null);
    }
}