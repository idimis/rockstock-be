package com.rockstock.backend.infrastructure.product.repository;

import com.rockstock.backend.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE LOWER(p.productName) = LOWER(:productName) AND p.deletedAt IS NULL")
    boolean existsByProductNameIgnoreCase(@Param("productName") String productName);
}