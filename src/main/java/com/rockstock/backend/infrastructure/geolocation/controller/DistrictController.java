package com.rockstock.backend.infrastructure.geolocation.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.service.geolocation.GetDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/geolocations/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final GetDistrictService getDistrictService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllDistricts() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all districts success!", getDistrictService.getAllDistricts());
    }

    @GetMapping("/provinces")
    public ResponseEntity<?> getDistrictsByProvinceId(@RequestParam Long provinceId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get district by province id success!", getDistrictService.getDistrictsByProvinceId(provinceId));
    }

    @GetMapping("/provinces/province")
    public ResponseEntity<?> getDistrictsByProvinceName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get district by province name success!", getDistrictService.getDistrictsByProvinceName(name));
    }

    @GetMapping("/cities")
    public ResponseEntity<?> getDistrictsByCityId(@RequestParam Long cityId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get district by city id success!", getDistrictService.getDistrictsByCityId(cityId));
    }

    @GetMapping("/cities/city")
    public ResponseEntity<?> getDistrictsByCityName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get district by city name success!", getDistrictService.getDistrictsByCityName(name));
    }

    @GetMapping
    public ResponseEntity<?> getByDistrictId(@RequestParam Long districtId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get district by id success!", getDistrictService.getByDistrictId(districtId));
    }

    @GetMapping("/district")
    public ResponseEntity<?> getByDistrictName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get district by name success!", getDistrictService.getByDistrictName(name));
    }
}
