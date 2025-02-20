package com.rockstock.backend.infrastructure.warehouseStock.repository;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.stock.WarehouseStock;
import com.rockstock.backend.entity.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {
    Optional<WarehouseStock> findByProductAndWarehouse(Product product, Warehouse warehouse);

//    @Query("SELECT COALESCE(SUM(s.stockQuantity), 0) FROM Stock s WHERE s.product.id = :productId")
//    BigDecimal getTotalStockByProduct(@Param("productId") Long productId);
}