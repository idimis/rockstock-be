package com.rockstock.backend.service.product;

import com.rockstock.backend.entity.product.ProductCategory;
import com.rockstock.backend.infrastructure.productCategory.dto.*;
import com.rockstock.backend.infrastructure.productCategory.repository.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    public CreateProductCategoryResponseDTO createProductCategory(CreateProductCategoryRequestDTO createProductCategoryRequestDTO) {
        String categoryName = createProductCategoryRequestDTO.getCategoryName().trim();

        if (productCategoryRepository.existsByCategoryNameIgnoreCase(categoryName)) {
            throw new IllegalArgumentException("Category name already exists.");
        }

        ProductCategory productCategory = createProductCategoryRequestDTO.toEntity();
        productCategoryRepository.save(productCategory);

        String message = "Category " + categoryName + " has been registered.";
        return new CreateProductCategoryResponseDTO(message, productCategory.getCategoryName());
    }

    @Transactional
    public CreateProductCategoryResponseDTO updateProductCategory(UpdateProductCategoryRequestDTO updateProductCategoryRequestDTO) {
        ProductCategory productCategory = productCategoryRepository.findById(updateProductCategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Product category not found: " + updateProductCategoryRequestDTO.getCategoryId()));

        if (updateProductCategoryRequestDTO.getCategoryName() != null && !updateProductCategoryRequestDTO.getCategoryName().isBlank()) {
            if (!productCategory.getCategoryName().trim().equalsIgnoreCase(updateProductCategoryRequestDTO.getCategoryName()) &&
                    productCategoryRepository.existsByCategoryNameIgnoreCase(updateProductCategoryRequestDTO.getCategoryName())) {
                throw new IllegalArgumentException("Category name already exists: " + updateProductCategoryRequestDTO.getCategoryName());
            }
            productCategory.setCategoryName(updateProductCategoryRequestDTO.getCategoryName());
        }

        if (updateProductCategoryRequestDTO.getCategoryPicture() != null && !updateProductCategoryRequestDTO.getCategoryPicture().isBlank()) {
            productCategory.setCategoryPicture(updateProductCategoryRequestDTO.getCategoryPicture());
        }

        ProductCategory updatedCategory = productCategoryRepository.save(productCategory);

        return new CreateProductCategoryResponseDTO("Category updated successfully", updatedCategory.getCategoryName());
    }

    @Transactional
    public void deleteProductCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Product category not found: " + categoryId));

        productCategoryRepository.delete(productCategory);
    }

    public List<HomeProductCategoryDTO> getAllCategories() {
        return productCategoryRepository.findAll()
                .stream()
                .map(category -> new HomeProductCategoryDTO(
                        category.getCategoryId(),
                        category.getCategoryName(),
                        category.getCategoryPicture()))
                .collect(Collectors.toList());
    }

    public GetProductCategoryResponseDTO getProductCategoryById(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product Category with ID " + id + " not found"));

        // Return a DTO instead of the entity
        return new GetProductCategoryResponseDTO(productCategory.getCategoryId(), productCategory.getCategoryName());
    }
}