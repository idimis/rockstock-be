package com.rockstock.backend.infrastructure.mutationJournal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutationJournalResponseDTO {
    private Long journalId;
    private Long productId;
    private String productName;
    private Long originWarehouseId;
    private String originWarehouseName;
    private Long destinationWarehouseId;
    private String destinationWarehouseName;
    private Long previousStockQuantity;
    private Long newStockQuantity;
    private String changeType;
    private String status;
}