package com.rockstock.backend.infrastructure.usecase.warehouse.controller;

import com.rockstock.backend.infrastructure.usecase.warehouse.dto.WarehouseCreateRequestDTO;
import com.rockstock.backend.infrastructure.usecase.warehouse.dto.WarehouseResponseDTO;
import com.rockstock.backend.infrastructure.usecase.warehouse.dto.WarehouseUpdateRequestDTO;
import com.rockstock.backend.infrastructure.usecase.warehouse.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<WarehouseResponseDTO> createWarehouse(@RequestBody WarehouseCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(warehouseService.createWarehouse(requestDTO));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<WarehouseResponseDTO>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponseDTO> updateWarehouse(@PathVariable Long warehouseId, @RequestBody WarehouseUpdateRequestDTO requestDTO) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(warehouseId, requestDTO));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/{warehouseId}/assign-admin/{adminId}")
    public ResponseEntity<Void> assignWarehouseAdmin(@PathVariable Long warehouseId, @PathVariable Long adminId) {
        warehouseService.assignWarehouseAdmin(warehouseId, adminId);
        return ResponseEntity.ok().build();
    }
}
