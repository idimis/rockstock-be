package com.rockstock.backend.infrastructure.geolocation.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.service.geolocation.GetCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/geolocations/cities")
@RequiredArgsConstructor
public class CityController {
    private final GetCityService getCityService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCities() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all cities success!", getCityService.getAllCities());
    }

    @GetMapping("/provinces")
    public ResponseEntity<?> getCitiesByProvinceId(@RequestParam Long provinceId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get city by province id success!", getCityService.getCitiesByProvinceId(provinceId));
    }

    @GetMapping("/provinces/province")
    public ResponseEntity<?> getCitiesByProvinceName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get city by province name success!", getCityService.getCitiesByProvinceName(name));
    }

    @GetMapping
    public ResponseEntity<?> getByCityId(@RequestParam Long cityId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get city by id success!", getCityService.getByCityId(cityId));
    }

    @GetMapping("/city")
    public ResponseEntity<?> getByCityName(@RequestParam String name) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get city by name success!", getCityService.getByCityName(name));
    }
}
