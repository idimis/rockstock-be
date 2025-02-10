package com.rockstock.backend.usecase.product;

import com.rockstock.backend.entity.product.ProductCategory;
import com.rockstock.backend.infrastructure.productCategory.dto.CreateProductCategoryRequestDTO;
import com.rockstock.backend.infrastructure.productCategory.dto.CreateProductCategoryResponseDTO;
import com.rockstock.backend.infrastructure.productCategory.dto.GetProductCategoryResponseDTO;
import com.rockstock.backend.infrastructure.productCategory.dto.UpdateProductCategoryRequestDTO;
import com.rockstock.backend.infrastructure.productCategory.repository.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

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

    public List<GetProductCategoryResponseDTO> getAllCategories() {
        return productCategoryRepository.findAll()
                .stream()
                .map(category -> new GetProductCategoryResponseDTO(
                        category.getCategoryId(),
                        category.getCategoryName(),
                        category.getCategoryPicture()))
                .collect(Collectors.toList());
    }
}