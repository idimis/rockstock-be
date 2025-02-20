package com.rockstock.backend.service.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductPicture;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureRequestDTO;
import com.rockstock.backend.infrastructure.productPicture.dto.CreateProductPictureResponseDTO;
import com.rockstock.backend.infrastructure.productPicture.repository.ProductPictureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPictureService {
    private final ProductPictureRepository productPictureRepository;
    private final ProductRepository productRepository;
    private final Cloudinary cloudinary;

    private boolean isValidFileType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.contains("jpeg") || contentType.contains("jpg") ||
                contentType.contains("png") || contentType.contains("gif"));
    }

    @SuppressWarnings("unchecked")
    private String uploadToCloudinary(MultipartFile file) throws IOException {
        if (!isValidFileType(file)) {
            throw new IllegalArgumentException("Invalid file type. Only .jpg, .jpeg, .png, and .gif are allowed.");
        }

        if (file.getSize() > 1 * 1024 * 1024) { // 1MB limit
            throw new IllegalArgumentException("File size must be less than 1MB.");
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url"); // Cloudinary URL
    }

    @Transactional
    public CreateProductPictureResponseDTO createProductPicture(CreateProductPictureRequestDTO createProductPictureRequestDTO, MultipartFile file) throws IOException {
        // Retrieve product using productId from the DTO
        Product product = productRepository.findById(createProductPictureRequestDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Limit the number of pictures for a product to 5
        long pictureCount = productPictureRepository.countByProductId(product.getId());
        if (pictureCount >= 5) {
            throw new IllegalStateException("A product can only have up to 5 pictures.");
        }

        // Upload file to cloud storage (assumed method)
        String imageUrl = uploadToCloudinary(file);

        // If this picture should be marked as main, reset any previous main picture
        if (createProductPictureRequestDTO.isMain()) {
            productPictureRepository.resetMainPicture(product.getId());
        }

        // Create new ProductPicture object and save it
        ProductPicture productPicture = new ProductPicture();
        productPicture.setProduct(product);
        productPicture.setProductPictureUrl(imageUrl);
        productPicture.setPosition(createProductPictureRequestDTO.getPosition());
        productPicture.setIsMain(createProductPictureRequestDTO.isMain());

        // Save and return the saved picture details
        ProductPicture savedPicture = productPictureRepository.save(productPicture);

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

        productPictureRepository.updatePositionsAfterDeletion(productId, deletedPosition);
    }

    @Transactional
    public void updateMainPicture(Long productId, Long pictureId) {
        // Find the picture by productId and pictureId, ensuring it's not deleted
        ProductPicture picture = productPictureRepository.findByProductIdAndPictureId(productId, pictureId)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found or has been deleted"));

        // Reset existing main picture for the product
        productPictureRepository.resetMainPicture(productId);

        // Set the new main picture
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