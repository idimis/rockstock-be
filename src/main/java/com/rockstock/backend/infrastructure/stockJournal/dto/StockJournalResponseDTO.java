package com.rockstock.backend.infrastructure.stockJournal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockJournalResponseDTO {
    private Long stockJournalId;
    private Long productId;
    private String productName;
    private Long originWarehouseId;
    private String originWarehouseName;
    private Long destinationWarehouseId;
    private String destinationWarehouseName;
    private Long previousStockQuantity;
    private Long newStockQuantity;
    private String ChangeType;
    private String Status;
}