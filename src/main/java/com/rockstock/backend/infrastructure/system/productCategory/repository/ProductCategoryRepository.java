package com.rockstock.backend.infrastructure.system.productCategory.repository;

import com.rockstock.backend.entity.product.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    boolean existsByCategoryName(@NotBlank String categoryName);
}
