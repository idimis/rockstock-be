package com.rockstock.backend.infrastructure.warehouse.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.service.warehouse.FindNearestWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final FindNearestWarehouseService findNearestWarehouseService;

    @GetMapping("/nearest")
    public ResponseEntity<?> findNearestWarehouse(
            @RequestParam String latitude,
            @RequestParam String longitude
    ) {
        return ApiResponse.success(HttpStatus.OK.value(), "Nearest warehouse found!", findNearestWarehouseService.findNearestWarehouse(latitude, longitude));
    }
}
