package com.rockstock.backend.service.productCategory;

import com.rockstock.backend.entity.product.ProductCategory;
import com.rockstock.backend.infrastructure.productCategory.dto.*;
import com.rockstock.backend.infrastructure.productCategory.repository.ProductCategoryRepository;
import com.rockstock.backend.service.cloudinary.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public CreateProductCategoryResponseDTO createProductCategory(CreateProductCategoryRequestDTO createProductCategoryRequestDTO, MultipartFile file) throws IOException {
        String categoryName = createProductCategoryRequestDTO.getCategoryName().trim();

        if (productCategoryRepository.existsByCategoryName(categoryName)) {
            throw new IllegalArgumentException("Category name already exists.");
        }

        String imageUrl;
            try {
                imageUrl = cloudinaryService.uploadFile(file);
            } catch (Exception e) {
                throw new IOException("Failed to upload image to Cloudinary.", e);
        }

        // Create new ProductCategory entity
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(categoryName);
        productCategory.setCategoryPicture(imageUrl); // Store uploaded image URL

        ProductCategory savedCategory = productCategoryRepository.save(productCategory);

        return new CreateProductCategoryResponseDTO(
                savedCategory.getCategoryId(),
                savedCategory.getCategoryName(),
                savedCategory.getCategoryPicture()
        );
    }

    @Transactional
    public CreateProductCategoryResponseDTO updateProductCategory(UpdateProductCategoryRequestDTO requestDTO) throws IOException {
        ProductCategory productCategory = productCategoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + requestDTO.getCategoryId()));

        // Update category name if provided
        if (requestDTO.getCategoryName() != null && !requestDTO.getCategoryName().isBlank() &&
                !productCategory.getCategoryName().equalsIgnoreCase(requestDTO.getCategoryName())) {

            if (productCategoryRepository.existsByCategoryName(requestDTO.getCategoryName())) {
                throw new IllegalArgumentException("Category name already exists: " + requestDTO.getCategoryName());
            }
            productCategory.setCategoryName(requestDTO.getCategoryName());
        }

        // Upload new picture if provided
        if (requestDTO.getFile() != null && !requestDTO.getFile().isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(requestDTO.getFile());
            productCategory.setCategoryPicture(imageUrl);
        }

        ProductCategory updatedCategory = productCategoryRepository.save(productCategory);

        return new CreateProductCategoryResponseDTO(
                updatedCategory.getCategoryId(),
                updatedCategory.getCategoryName(),
                updatedCategory.getCategoryPicture()
        );
    }

    @Transactional
    public void softDeleteProductCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        productCategory.setDeletedAt(OffsetDateTime.now());
        productCategoryRepository.save(productCategory);
    }

    public void restoreProductCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found or not deleted"));

        productCategory.setDeletedAt(null);
        productCategoryRepository.save( productCategory);
    }

    public List<HomeProductCategoryDTO> getAllCategories() {
        return productCategoryRepository.findAllActiveCategories()
                .stream()
                .map(category -> new HomeProductCategoryDTO(
                        category.getCategoryId(),
                        category.getCategoryName(),
                        category.getCategoryPicture()))
                .collect(Collectors.toList());
    }

    public GetProductCategoryResponseDTO getProductCategoryById(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Product Category with ID " + categoryId + " not found"));

        // Return a DTO instead of the entity
        return new GetProductCategoryResponseDTO(productCategory.getCategoryId(), productCategory.getCategoryName());
    }
}