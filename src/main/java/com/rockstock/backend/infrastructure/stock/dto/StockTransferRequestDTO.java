package com.rockstock.backend.infrastructure.stock.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferRequestDTO {
    @NotNull
    private Long productId;

    @NotNull
    private Long originWarehouseId;

    @NotNull
    private Long destinationWarehouseId;

    @NotNull
    private Long stockQuantity;
}