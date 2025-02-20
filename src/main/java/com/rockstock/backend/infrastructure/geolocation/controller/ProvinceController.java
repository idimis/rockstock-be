package com.rockstock.backend.infrastructure.geolocation.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.service.geolocation.GetProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/geolocations/provinces")
@RequiredArgsConstructor
public class ProvinceController {
    private final GetProvinceService getProvinceService;

    // Province
    @GetMapping("/all")
    public ResponseEntity<?> getAllProvinces() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all provinces success!", getProvinceService.getAllProvinces());
    }

    @GetMapping("/province")
    public ResponseEntity<?> getByProvinceName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get province by name success!", getProvinceService.getByProvinceName(name));
    }

    @GetMapping
    public ResponseEntity<?> getByProvinceId(@RequestParam Long provinceId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get province by id success!", getProvinceService.getByProvinceId(provinceId));
    }
}
