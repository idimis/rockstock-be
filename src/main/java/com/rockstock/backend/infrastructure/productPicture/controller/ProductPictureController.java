package com.rockstock.backend.infrastructure.productPicture.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.productPicture.dto.*;
import com.rockstock.backend.service.productPicture.ProductPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pictures")
@RequiredArgsConstructor
public class ProductPictureController {
    private final ProductPictureService productPictureService;

    @PostMapping("/create")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<CreateProductPictureResponseDTO>> createProductPicture(
            @RequestParam("file") MultipartFile file,
            @RequestPart("request") String requestJson) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        CreateProductPictureRequestDTO requestDTO = objectMapper.readValue(requestJson, CreateProductPictureRequestDTO.class);

        CreateProductPictureResponseDTO response = productPictureService.createProductPicture(requestDTO, file);
        return ApiResponse.success("Create new category success", response);
    }

    // 📌 Update Picture Position (Drag & Drop Reordering)
    @PatchMapping("/update-position")
    public ResponseEntity<UpdatePicturePositionResponseDTO> updateProductPicturePosition(
            @RequestBody UpdatePicturePositionRequestDTO requestDTO) {
        UpdatePicturePositionResponseDTO responseDTO = productPictureService.updateProductPicturePosition(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 📌 Get All Product Pictures
    @GetMapping("/{productId}")
    public ResponseEntity<List<GetProductPicturesResponseDTO>> getAllProductPictures(@PathVariable Long productId) {
        List<GetProductPicturesResponseDTO> pictures = productPictureService.getAllProductPictures(productId);
        return ResponseEntity.ok(pictures);
    }

    @PatchMapping("/{productId}/{pictureId}/soft-delete")
    public ResponseEntity<String> softDeleteProductPicture(
            @PathVariable Long productId,
            @PathVariable Long pictureId) {
        productPictureService.softDeleteProductPicture(productId, pictureId);
        return ResponseEntity.ok("Product picture soft deleted successfully.");
    }

    @PatchMapping("/{productId}/{pictureId}/restore")
    public ResponseEntity<String> restoreProductPicture(
            @PathVariable Long productId,
            @PathVariable Long pictureId) {
        productPictureService.restoreProductPicture(productId, pictureId);
        return ResponseEntity.ok("Product picture restored successfully.");
    }
}