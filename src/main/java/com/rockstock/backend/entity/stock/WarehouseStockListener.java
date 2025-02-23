package com.rockstock.backend.entity.stock;

import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.warehouseStock.repository.WarehouseStockRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WarehouseStockListener {

    @Autowired
    private WarehouseStockRepository warehouseStockRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void updateTotalStock(WarehouseStock warehouseStock) {
        Long productId = warehouseStock.getProduct().getId();
        BigDecimal totalStock = warehouseStockRepository.getTotalStockByProductId(productId);

        // Update the totalStock in the Product table
        productRepository.updateTotalStock(productId, totalStock);
    }
}