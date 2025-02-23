package com.rockstock.backend.infrastructure.warehouseStock.controller;

import com.rockstock.backend.infrastructure.warehouseStock.dto.WarehouseStockResponseDTO;
import com.rockstock.backend.service.warehouseStock.WarehouseStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouse-stocks")
@RequiredArgsConstructor
public class WarehouseStockController {

    private final WarehouseStockService warehouseStockService;

    @PostMapping("/create")
    public ResponseEntity<WarehouseStockResponseDTO> createWarehouseStock(
            @RequestParam Long productId,
            @RequestParam Long warehouseId) {
        WarehouseStockResponseDTO response = warehouseStockService.createWarehouseStock(productId, warehouseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{stockId}/soft-delete")
    public ResponseEntity<String> softDeleteWarehouseStock(@PathVariable Long stockId) {
        warehouseStockService.softDeleteWarehouseStock(stockId);
        return ResponseEntity.ok("WarehouseStock soft deleted successfully.");
    }

    @PutMapping("/{stockId}/restore")
    public ResponseEntity<String> restoreWarehouseStock(@PathVariable Long stockId) {
        warehouseStockService.restoreWarehouseStock(stockId);
        return ResponseEntity.ok("WarehouseStock restored successfully.");
    }


}