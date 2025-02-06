package com.rockstock.backend.infrastructure.usecase.geolocation.service;

import com.rockstock.backend.infrastructure.usecase.geolocation.dto.CityResponse;

import java.util.List;

public interface CityService {
    List<CityResponse> getCitiesByProvince(Long provinceId);
    CityResponse getCityById(Long cityId);
    CityResponse createCity(CreateCityRequest request);
}
