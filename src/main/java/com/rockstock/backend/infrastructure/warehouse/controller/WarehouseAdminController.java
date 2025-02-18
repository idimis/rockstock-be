//package com.rockstock.backend.infrastructure.warehouse.controller;
//
//import com.rockstock.backend.infrastructure.warehouse.dto.AssignWarehouseAdminRequestDTO;
//import com.rockstock.backend.service.warehouse.WarehouseService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/warehouse-admins")
//@RequiredArgsConstructor
//public class WarehouseAdminController {
//    private final WarehouseService warehouseService;
//
//    @PostMapping("/assign")
//    public ResponseEntity<Void> assignWarehouseAdmin(@RequestBody AssignWarehouseAdminRequestDTO requestDTO) {
//        warehouseService.assignWarehouseAdmin(requestDTO);
//        return ResponseEntity.ok().build();
//    }
//}
