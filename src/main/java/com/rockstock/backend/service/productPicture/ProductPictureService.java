package com.rockstock.backend.service.productPicture;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductPicture;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.productPicture.dto.*;
import com.rockstock.backend.infrastructure.productPicture.repository.ProductPictureRepository;
import com.rockstock.backend.service.cloudinary.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPictureService {
    private final ProductPictureRepository productPictureRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public CreateProductPictureResponseDTO createProductPicture(CreateProductPictureRequestDTO createProductPictureRequestDTO, MultipartFile file) throws IOException {
        // Retrieve product using productId from the DTO
        Product product = productRepository.findByIdAndDeletedAtIsNull(createProductPictureRequestDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Limit the number of pictures for a product to 5
        long pictureCount = productPictureRepository.countByProductId(product.getId());
        if (pictureCount >= 3) {
            throw new IllegalStateException("A product can only have up to 3 pictures.");
        }

        int position = createProductPictureRequestDTO.getPosition();
        if (position < 1 || position > 3) {
            throw new IllegalArgumentException("Picture position must be between 1 and 3.");
        }

        // Ensure uniqueness of position (1-3) for a product
        if (productPictureRepository.existsByProductIdAndPosition(product.getId(), position)) {
            throw new IllegalStateException("A picture already exists at position " + position + " for this product.");
        }

        // Upload file to cloud storage (assumed method)
        String imageUrl;
        try {
            imageUrl = cloudinaryService.uploadFile(file);
        } catch (Exception e) {
            throw new IOException("Failed to upload image to Cloudinary.", e);
        }

        // Create new ProductPicture object and save it
        ProductPicture productPicture = new ProductPicture();
        productPicture.setProduct(product);
        productPicture.setProductPictureUrl(imageUrl);
        productPicture.setPosition(position);

        // Save and return the saved picture details
        ProductPicture savedPicture = productPictureRepository.save(productPicture);

        return new CreateProductPictureResponseDTO(
                savedPicture.getPictureId(),
                savedPicture.getProductPictureUrl(),
                savedPicture.getPosition()
        );
    }

    @Transactional
    public UpdatePicturePositionResponseDTO updateProductPicturePosition(UpdatePicturePositionRequestDTO requestDTO) {
        // Step 1: Find the picture by pictureId AND productId (ensuring it's not deleted)
        ProductPicture productPicture = productPictureRepository
                .findByProductIdAndPictureId(requestDTO.getProductId(), requestDTO.getPictureId())
                .orElseThrow(() -> new EntityNotFoundException("Picture not found for this product or has been deleted"));

        int newPosition = requestDTO.getNewPosition();

        // Step 2: Ensure new position is valid (between 1 and 3)
        if (newPosition < 1 || newPosition > 3) {
            throw new IllegalArgumentException("Picture position must be between 1 and 3.");
        }

        // Step 3: Check if another picture already occupies that position
        Optional<ProductPicture> existingPictureOpt = productPictureRepository
                .findByProductIdAndPosition(requestDTO.getProductId(), newPosition);

        existingPictureOpt.ifPresent(existingPicture -> {
            // Swap positions
            existingPicture.setPosition(productPicture.getPosition());
            productPictureRepository.save(existingPicture);
        });

        // Step 4: Update the requested picture's position
        productPicture.setPosition(newPosition);
        ProductPicture updatedPicture = productPictureRepository.save(productPicture);

        return new UpdatePicturePositionResponseDTO(
                updatedPicture.getPictureId(),
                updatedPicture.getProductPictureUrl(),
                updatedPicture.getPosition()
        );
    }

    public List<GetProductPicturesResponseDTO> getAllProductPictures(Long productId) {
        List<ProductPicture> pictures = productPictureRepository.findAllByProductId(productId);

        return pictures.stream()
                .map(pic -> new GetProductPicturesResponseDTO(
                        pic.getProductPictureUrl(),
                        pic.getPosition()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void softDeleteProductPicture(Long productId, Long pictureId) {
        OffsetDateTime now = OffsetDateTime.now();
        int updatedRows = productPictureRepository.softDelete(productId, pictureId, now);

        if (updatedRows == 0) {
            throw new EntityNotFoundException("Picture not found or already deleted.");
        }
    }

    @Transactional
    public void restoreProductPicture(Long productId, Long pictureId) {
        int updatedRows = productPictureRepository.restoreDeletedPicture(productId, pictureId);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("Picture not found or not deleted.");
        }
    }
}