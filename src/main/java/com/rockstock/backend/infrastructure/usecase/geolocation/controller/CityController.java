package com.rockstock.backend.infrastructure.usecase.geolocation.controller;

import com.rockstock.backend.infrastructure.usecase.geolocation.dto.CityResponse;
import com.rockstock.backend.infrastructure.usecase.geolocation.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping("/province/{provinceId}")
    public List<CityResponse> getCitiesByProvince(@PathVariable Long provinceId) {
        return cityService.getCitiesByProvince(provinceId);
    }

}
