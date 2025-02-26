package com.rockstock.backend.infrastructure.product.repository;

import com.rockstock.backend.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
    List<Product> findAllActiveProducts();

    @Query("SELECT p FROM Product p WHERE p.id = :productId AND p.deletedAt IS NULL")
    Optional<Product> findByIdAndDeletedAtIsNull(Long productId);

    List<Product> findAllByDeletedAtIsNull();
}
