package com.rockstock.backend.infrastructure.product.repository;

import com.rockstock.backend.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
    List<Product> findAllActiveProducts();

    Optional<Product> findByIdAndDeletedAtIsNull(Long id);
    List<Product> findAllByDeletedAtIsNull();
}