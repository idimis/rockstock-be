package com.rockstock.backend.service.geolocation;

import com.rockstock.backend.infrastructure.geolocation.dto.GetCityResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GetCityService {
    List<GetCityResponseDTO> getCitiesByProvince(Long provinceId);
    List<GetCityResponseDTO> getCityByName(String name);
    List<GetCityResponseDTO> getAllCities();
}
