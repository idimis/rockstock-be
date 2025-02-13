package com.rockstock.backend.usecase.product;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductPicture;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureRequestDTO;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureResponseDTO;
import com.rockstock.backend.infrastructure.productPicture.repository.ProductPictureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductPictureService {
    private final ProductPictureRepository productPictureRepository;
    private final ProductRepository productRepository;

    public ProductPictureService(ProductPictureRepository productPictureRepository, ProductRepository productRepository) {
        this.productPictureRepository = productPictureRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateProductPictureResponseDTO createProductPicture(CreateProductPictureRequestDTO createProductPictureRequestDTO) {
        Product product = productRepository.findById(createProductPictureRequestDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Count existing pictures
        long pictureCount = productPictureRepository.countByProductId(product.getId());
        if (pictureCount >= 5) {
            throw new IllegalStateException("A product can only have up to 5 pictures.");
        }

        // If `isMain` is true, update other pictures to false
        if (createProductPictureRequestDTO.isMain()) {
            productPictureRepository.resetMainPicture(product.getId());
        }

        // Create and save the new product picture
        ProductPicture productPicture = new ProductPicture();
        productPicture.setProduct(product);
        productPicture.setProductPictureUrl(createProductPictureRequestDTO.getProductPictureUrl());
        productPicture.setPosition(createProductPictureRequestDTO.getPosition());
        productPicture.setIsMain(createProductPictureRequestDTO.isMain());

        ProductPicture savedPicture = productPictureRepository.save(productPicture);

        // Return a response DTO
        return new CreateProductPictureResponseDTO(
                savedPicture.getPictureId(),
                savedPicture.getProductPictureUrl(),
                savedPicture.getIsMain(),
                savedPicture.getPosition()
        );
    }

    @Transactional
    public void deleteProductPicture(Long pictureId) {
        ProductPicture picture = productPictureRepository.findById(pictureId)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found"));

        int deletedPosition = picture.getPosition();
        Long productId = picture.getProduct().getId();

        productPictureRepository.delete(picture);

        // Shift positions of remaining pictures
        productPictureRepository.updatePositionsAfterDeletion(productId, deletedPosition);
    }

    @Transactional
    public void updateMainPicture(Long pictureId) {
        ProductPicture picture = productPictureRepository.findById(pictureId)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found"));

        // Reset all other pictures' `isMain` to false
        productPictureRepository.resetMainPicture(picture.getProduct().getId());

        // Set selected picture as main
        picture.setIsMain(true);
        productPictureRepository.save(picture);
    }

    @Transactional
    public List<CreateProductPictureResponseDTO> getProductPicturesByProductId(Long productId) {
        List<ProductPicture> pictures = productPictureRepository.findByProductIdOrderByPositionAsc(productId);

        return pictures.stream()
                .map(picture -> new CreateProductPictureResponseDTO(
                        picture.getPictureId(),
                        picture.getProductPictureUrl(),
                        picture.getIsMain(),
                        picture.getPosition()
                ))
                .collect(Collectors.toList());
    }
}