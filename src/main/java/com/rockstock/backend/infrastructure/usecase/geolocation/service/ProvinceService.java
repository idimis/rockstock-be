package com.rockstock.backend.infrastructure.usecase.geolocation.service;

import com.rockstock.backend.infrastructure.usecase.geolocation.dto.ProvinceResponse;

import java.util.List;

public interface ProvinceService {
    List<ProvinceResponse> getAllProvinces();
    ProvinceResponse createProvince(CreateProvinceRequest request);
}
