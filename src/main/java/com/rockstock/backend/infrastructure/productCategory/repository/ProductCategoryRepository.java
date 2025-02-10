package com.rockstock.backend.infrastructure.productCategory.repository;

import com.rockstock.backend.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Query("SELECT COUNT(p) > 0 FROM ProductCategory p WHERE LOWER(p.categoryName) = LOWER(:categoryName)")
    boolean existsByCategoryNameIgnoreCase(@Param("categoryName") String categoryName);
}