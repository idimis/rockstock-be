package com.rockstock.backend.infrastructure.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDTO {
    private Long stockId;
    private Long productId;
    private String productName;
    private Long warehouseId;
    private String warehouseName;
    private Long stockQuantity;
}