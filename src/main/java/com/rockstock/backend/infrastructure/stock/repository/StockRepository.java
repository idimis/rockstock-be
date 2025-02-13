package com.rockstock.backend.infrastructure.stock.repository;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.stock.Stock;
import com.rockstock.backend.entity.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductAndWarehouse(Product product, Warehouse warehouse);

    @Query("SELECT COALESCE(SUM(s.stockQuantity), 0) FROM Stock s WHERE s.product.id = :productId")
    BigDecimal getTotalStockByProduct(@Param("productId") Long productId);
}