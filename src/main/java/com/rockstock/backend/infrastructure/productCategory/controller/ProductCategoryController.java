package com.rockstock.backend.infrastructure.productCategory.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.productCategory.dto.*;
import com.rockstock.backend.service.productCategory.ProductCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

//    @PreAuthorize("hasRole('SUPER ADMIN')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateProductCategoryResponseDTO> createCategory(
//            Long sellerId = Claims.getUserIdFromJwt();
            @RequestParam("categoryName") String categoryName,
            @RequestParam("file") MultipartFile file) throws IOException {

        CreateProductCategoryRequestDTO requestDTO = new CreateProductCategoryRequestDTO(categoryName, null);
        CreateProductCategoryResponseDTO responseDTO = productCategoryService.createProductCategory(requestDTO, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

//    @PreAuthorize("hasRole('SELLER')")
    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateProductCategoryResponseDTO> updateCategory(
            @ModelAttribute UpdateProductCategoryRequestDTO requestDTO) throws IOException {

        CreateProductCategoryResponseDTO responseDTO = productCategoryService.updateProductCategory(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable Long categoryId) {
        productCategoryService.softDeleteProductCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully");
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

    @PatchMapping("/restore/{categoryId}")
    public ResponseEntity<String> restoreProduct(@PathVariable Long categoryId) {
        productCategoryService.restoreProductCategory(categoryId);
        return ResponseEntity.ok("Category restored successfully");
    }
}