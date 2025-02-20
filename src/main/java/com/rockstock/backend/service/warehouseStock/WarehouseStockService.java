package com.rockstock.backend.service.warehouseStock;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.stock.WarehouseStock;
import com.rockstock.backend.entity.warehouse.Warehouse;
import com.rockstock.backend.infrastructure.warehouseStock.repository.WarehouseStockRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.warehouse.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseStockService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseStockRepository warehouseStockRepository;

    public WarehouseStock createWarehouseStockIfNotExists(Long productId, Long warehouseId) {
        // Check if product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if warehouse exists
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        // Check if WarehouseStock already exists
        Optional<WarehouseStock> existingStock = warehouseStockRepository.findByProductAndWarehouse(product, warehouse);
        if (existingStock.isPresent()) {
            throw new RuntimeException("WarehouseStock already exists for this product in the specified warehouse");
        }

        // Create new WarehouseStock with stockQuantity = 0
        WarehouseStock newStock = new WarehouseStock();
        newStock.setProduct(product);
        newStock.setWarehouse(warehouse);
        newStock.setStockQuantity(0L);

        return warehouseStockRepository.save(newStock);
    }
}