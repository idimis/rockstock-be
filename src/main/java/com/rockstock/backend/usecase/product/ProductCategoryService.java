package com.rockstock.backend.usecase.product;

import com.rockstock.backend.entity.product.ProductCategory;
import com.rockstock.backend.infrastructure.system.productCategory.dto.CreateProductCategoryRequestDTO;
import com.rockstock.backend.infrastructure.system.productCategory.dto.CreateProductCategoryResponseDTO;
import com.rockstock.backend.infrastructure.system.productCategory.dto.UpdateProductCategoryRequestDTO;
import com.rockstock.backend.infrastructure.system.productCategory.repository.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    public CreateProductCategoryResponseDTO createProductCategory(CreateProductCategoryRequestDTO createProductCategoryRequestDTO) {
        if (productCategoryRepository.existsByCategoryName(createProductCategoryRequestDTO.getCategoryName())) {
            throw new IllegalArgumentException("Category name already exists.");
        }

        ProductCategory productCategory = createProductCategoryRequestDTO.toEntity();
        productCategoryRepository.save(productCategory);

        String message = "Category " + createProductCategoryRequestDTO.getCategoryName() + " has been registered.";
        return new CreateProductCategoryResponseDTO(message, productCategory.getCategoryName());
    }

    @Transactional
    public CreateProductCategoryResponseDTO updateProductCategory(UpdateProductCategoryRequestDTO updateProductCategoryRequestDTO) {
        ProductCategory productCategory = productCategoryRepository.findById(updateProductCategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Product category not found: " + updateProductCategoryRequestDTO.getCategoryId()));

        if (updateProductCategoryRequestDTO.getCategoryName() != null && !updateProductCategoryRequestDTO.getCategoryName().isBlank()) {
            if (!productCategory.getCategoryName().equals(updateProductCategoryRequestDTO.getCategoryName()) &&
                    productCategoryRepository.existsByCategoryName(updateProductCategoryRequestDTO.getCategoryName())) {
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
}
