package com.rockstock.backend.service.warehouse;

import com.rockstock.backend.infrastructure.warehouse.dto.AssignWarehouseAdminDTO;
import com.rockstock.backend.infrastructure.warehouse.dto.WarehouseResponseDTO;

public interface WarehouseAdminService {
    void assignWarehouseAdmin(AssignWarehouseAdminDTO request);
    void removeWarehouseAdmin(Long warehouseAdminId);
}
