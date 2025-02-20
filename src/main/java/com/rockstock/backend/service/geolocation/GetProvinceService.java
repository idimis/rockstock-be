package com.rockstock.backend.service.geolocation;

import com.rockstock.backend.infrastructure.geolocation.dto.GetProvinceResponseDTO;

import java.util.List;

public interface GetProvinceService {
    List<GetProvinceResponseDTO> getProvinceByName(String name);
    List<GetProvinceResponseDTO> getAllProvinces();
}
