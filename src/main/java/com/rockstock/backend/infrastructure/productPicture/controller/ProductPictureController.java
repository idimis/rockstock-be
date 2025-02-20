package com.rockstock.backend.infrastructure.productPicture.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureRequestDTO;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureResponseDTO;
import com.rockstock.backend.service.product.ProductPictureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pictures")
public class ProductPictureController {
    private final ProductPictureService productPictureService;

    @PostMapping("/{productId}/upload")
    public ResponseEntity<ApiResponse<CreateProductPictureResponseDTO>> createProductPicture(
            @PathVariable Long productId,  // Extract productId from the path
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") @Valid CreateProductPictureRequestDTO createProductPictureRequestDTO) throws IOException {

        // Set the productId inside the request DTO
        createProductPictureRequestDTO.setProductId(productId);

        // Call service to handle the creation of the product picture
        CreateProductPictureResponseDTO responseDTO = productPictureService.createProductPicture(createProductPictureRequestDTO, file);

        return ApiResponse.success("Picture added successfully", responseDTO);
    }

    @DeleteMapping("/{pictureId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductPicture(@PathVariable Long pictureId) {
        productPictureService.deleteProductPicture(pictureId);
        return ApiResponse.success("Picture deleted successfully", null);
    }

    @PutMapping("/{productId}/{pictureId}/set-main")
    public ResponseEntity<ApiResponse<Void>> updateMainPicture(
            @PathVariable Long productId,
            @PathVariable Long pictureId) {

        productPictureService.updateMainPicture(productId, pictureId);
        return ApiResponse.success("Main picture updated successfully", null);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<List<CreateProductPictureResponseDTO>>> getProductPictures(
            @PathVariable Long productId) {
        List<CreateProductPictureResponseDTO> pictures = productPictureService.getProductPicturesByProductId(productId);

        return ApiResponse.success("Fetched product pictures successfully", pictures);
    }
}
