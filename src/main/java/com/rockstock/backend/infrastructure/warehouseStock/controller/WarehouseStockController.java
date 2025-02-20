package com.rockstock.backend.infrastructure.warehouseStock.controller;

import com.rockstock.backend.entity.stock.WarehouseStock;
import com.rockstock.backend.infrastructure.warehouseStock.dto.WarehouseStockRequestDTO;
import com.rockstock.backend.service.warehouseStock.WarehouseStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouse-stocks")
@RequiredArgsConstructor
public class WarehouseStockController {

    private final WarehouseStockService warehouseStockService;

    @PostMapping("/create")
    public ResponseEntity<?> createWarehouseStock(@RequestBody @Valid WarehouseStockRequestDTO requestDTO) {
        try {
            WarehouseStock warehouseStock = warehouseStockService.createWarehouseStockIfNotExists(
                    requestDTO.getProductId(),
                    requestDTO.getWarehouseId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(warehouseStock);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}