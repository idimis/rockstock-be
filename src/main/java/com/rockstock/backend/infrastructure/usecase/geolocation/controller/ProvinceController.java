package com.rockstock.backend.infrastructure.usecase.geolocation.controller;

import com.rockstock.backend.infrastructure.usecase.geolocation.dto.ProvinceResponse;
import com.rockstock.backend.infrastructure.usecase.geolocation.service.ProvinceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping
    public List<ProvinceResponse> getAllProvinces() {
        return provinceService.getAllProvinces();
    }

    @PostMapping
    public ProvinceResponse createProvince(@Valid @RequestBody CreateProvinceRequest request) {
        return provinceService.createProvince(request);
    }
}
