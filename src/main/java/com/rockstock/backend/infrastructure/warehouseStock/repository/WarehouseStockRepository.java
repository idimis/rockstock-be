package com.rockstock.backend.infrastructure.warehouseStock.repository;

import com.rockstock.backend.entity.stock.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {

    @Query("SELECT COALESCE(SUM(ws.stockQuantity), 0) FROM WarehouseStock ws WHERE ws.product.id = :productId AND ws.deletedAt IS NULL")
    BigDecimal getTotalStockByProductId(@Param("productId") Long productId);
}