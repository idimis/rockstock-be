package com.rockstock.backend.infrastructure.productCategory.repository;

import com.rockstock.backend.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Query("SELECT COUNT(pc) > 0 FROM ProductCategory pc WHERE LOWER(pc.categoryName) = LOWER(:categoryName) AND pc.deletedAt IS NULL")
    boolean existsByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT pc FROM ProductCategory pc WHERE pc.categoryId = :categoryId AND pc.deletedAt IS NULL")
    Optional<ProductCategory> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT pc FROM ProductCategory pc WHERE pc.deletedAt IS NULL")
    List<ProductCategory> findAllActiveCategories();
}