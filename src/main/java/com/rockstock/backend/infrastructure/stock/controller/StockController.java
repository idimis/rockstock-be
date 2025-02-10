package com.rockstock.backend.infrastructure.stock.controller;

import com.rockstock.backend.infrastructure.stock.dto.StockTransferRequestDTO;
import com.rockstock.backend.usecase.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // Endpoint untuk request transfer stock
    @PostMapping("/transfer-stock")
    public ResponseEntity<String> createTransferStock(@RequestBody StockTransferRequestDTO requestDTO) {
        stockService.transferStockService(requestDTO);
        return ResponseEntity.ok("Stock transfer request created successfully. Awaiting approval.");
    }

    // Endpoint untuk menyetujui atau membatalkan transfer stock
    @PutMapping("/approve-transfer/{journalId}")
    public ResponseEntity<String> approveTransfer(
            @PathVariable Long journalId,
            @RequestParam boolean isApproved) {

        stockService.processStockMutationService(journalId, isApproved);

        String message = isApproved ?
                "Stock transfer approved successfully." :
                "Stock transfer request canceled.";

        return ResponseEntity.ok(message);
    }
}