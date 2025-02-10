package com.rockstock.backend.infrastructure.geolocation.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.geolocation.dto.GetCityResponseDTO;
import com.rockstock.backend.service.geolocation.GetCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {
    private final GetCityService cityService;

    @GetMapping("/province/{provinceId}")
    public List<GetCityResponseDTO> getCitiesByProvince(@PathVariable Long provinceId) {
        return cityService.getCitiesByProvince(provinceId);
    }

    @GetMapping
    public ResponseEntity<?> getCityByName(@RequestParam String query){
        if(!query.isEmpty()){
            return ApiResponse.success(HttpStatus.OK.value(), "City(s) found !", cityService.getCityByName(query));
        }else {
            return ApiResponse.success(HttpStatus.OK.value(), "Cities found !", cityService.getAllCities());
        }
    }

}
