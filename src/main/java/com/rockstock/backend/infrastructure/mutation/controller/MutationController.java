package com.rockstock.backend.infrastructure.mutation.controller;

import com.rockstock.backend.infrastructure.mutation.dto.MutationRequestDTO;
import com.rockstock.backend.service.mutation.MutationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stocks")
@RequiredArgsConstructor
public class MutationController {

    private final MutationService mutationService;

    @PostMapping("/transfer-stock")
    public ResponseEntity<String> createTransferStock(@RequestBody MutationRequestDTO requestDTO) {
        mutationService.mutationRequestService(requestDTO);
        return ResponseEntity.ok("Stock transfer request created successfully. Awaiting approval.");
    }

    @PutMapping("/approve-transfer/{journalId}")
    public ResponseEntity<String> approveTransfer(
            @PathVariable Long journalId,
            @RequestParam boolean isApproved) {

        mutationService.processMutationRequestService(journalId, isApproved);

        String message = isApproved ?
                "Stock transfer approved successfully." :
                "Stock transfer request canceled.";

        return ResponseEntity.ok(message);
    }
}