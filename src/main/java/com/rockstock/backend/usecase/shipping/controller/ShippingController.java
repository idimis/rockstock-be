package com.rockstock.backend.usecase.shipping.controller;

import com.rockstock.backend.usecase.shipping.DTO.ShippingCostRequestDTO;
import com.rockstock.backend.usecase.shipping.DTO.ShippingCostResponseDTO;
import com.rockstock.backend.usecase.shipping.service.ShippingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ShippingCostResponseDTO> calculateShippingCost(@RequestBody ShippingCostRequestDTO requestDTO) {
        ShippingCostResponseDTO responseDTO = shippingService.calculateShippingCost(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
