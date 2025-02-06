package com.rockstock.backend.infrastructure.usecase.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignWarehouseAdminRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long warehouseId;
}
