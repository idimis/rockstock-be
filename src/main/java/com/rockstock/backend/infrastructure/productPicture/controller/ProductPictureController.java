package com.rockstock.backend.infrastructure.productPicture.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureRequestDTO;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureResponseDTO;
import com.rockstock.backend.usecase.product.ProductPictureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pictures")
public class ProductPictureController {
    private final ProductPictureService productPictureService;

    public ProductPictureController(ProductPictureService productPictureService) {
        this.productPictureService = productPictureService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreateProductPictureResponseDTO>> createProductPicture(@Valid @RequestBody CreateProductPictureRequestDTO createProductPictureRequestDTO) {
        CreateProductPictureResponseDTO createProductPictureResponseDTO = productPictureService.createProductPicture(createProductPictureRequestDTO);
        return ApiResponse.successfulResponse("Picture added successfully", createProductPictureResponseDTO);
    }

    @DeleteMapping("/pictures/{pictureId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductPicture(@PathVariable Long pictureId) {
        productPictureService.deleteProductPicture(pictureId);
        return ApiResponse.successfulResponse("Picture deleted successfully", null);
    }

    @PutMapping("/pictures/{pictureId}/set-main")
    public ResponseEntity<ApiResponse<Void>> updateMainPicture(@PathVariable Long pictureId) {
        productPictureService.updateMainPicture(pictureId);
        return ApiResponse.successfulResponse("Main picture updated successfully", null);
    }

    @GetMapping("/{productId}/pictures")
    public ResponseEntity<ApiResponse<List<CreateProductPictureResponseDTO>>> getProductPictures(
            @PathVariable Long productId) {
        List<CreateProductPictureResponseDTO> pictures = productPictureService.getProductPicturesByProductId(productId);
        return ApiResponse.successfulResponse("Fetched product pictures successfully", pictures);
    }
}
