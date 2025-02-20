package com.rockstock.backend.infrastructure.geolocation.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.service.geolocation.GetSubDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/geolocations/sub-districts")
@RequiredArgsConstructor
public class SubDistrictController {
    private final GetSubDistrictService getSubDistrictService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllSubDistricts() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all sub-district success!", getSubDistrictService.getAllSubDistricts());
    }

    @GetMapping("/provinces")
    public ResponseEntity<?> getSubDistrictsByProvinceId(@RequestParam Long provinceId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-districts by province id success!", getSubDistrictService.getSubDistrictsByProvinceId(provinceId));
    }

    @GetMapping("/provinces/province")
    public ResponseEntity<?> getSubDistrictsByProvinceName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-districts by province name success!", getSubDistrictService.getSubDistrictsByProvinceName(name));
    }

    @GetMapping("/cities")
    public ResponseEntity<?> getSubDistrictsByCityId(@RequestParam Long cityId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-districts by city id success!", getSubDistrictService.getSubDistrictsByCityId(cityId));
    }

    @GetMapping("/cities/city")
    public ResponseEntity<?> getSubDistrictsByCityName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-districts by city name success!", getSubDistrictService.getSubDistrictsByCityName(name));
    }

    @GetMapping("/districts")
    public ResponseEntity<?> getSubDistrictsByDistrictId(@RequestParam Long districtId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-districts by district id success!", getSubDistrictService.getSubDistrictsByDistrictId(districtId));
    }

    @GetMapping("/districts/district")
    public ResponseEntity<?> getSubDistrictsByDistrictName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-districts by district name success!", getSubDistrictService.getSubDistrictsByDistrictName(name));
    }

    @GetMapping
    public ResponseEntity<?> getBySubDistrictId(@RequestParam Long subDistrictId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-district by id success!", getSubDistrictService.getBySubDistrictId(subDistrictId));
    }

    @GetMapping("/sub-district")
    public ResponseEntity<?> getBySubDistrictName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get sub-district by name success!", getSubDistrictService.getBySubDistrictName(name));
    }
}
