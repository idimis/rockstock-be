package com.rockstock.backend.infrastructure.geolocation.controller;

import com.rockstock.backend.infrastructure.geolocation.dto.GetProvinceResponseDTO;
import com.rockstock.backend.service.geolocation.GetProvinceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {
    private final GetProvinceService provinceService;

    @GetMapping
    public List<GetProvinceResponseDTO> getAllProvinces() {
        return provinceService.getAllProvinces();
    }
}
