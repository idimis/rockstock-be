package com.rockstock.backend.service.geolocation;

import com.rockstock.backend.entity.geolocation.Province;

import java.util.List;
import java.util.Optional;

public interface GetProvinceService {

    List<Province> getAllProvinces();
    Optional<Province> getByProvinceName(String name);
    Optional<Province> getByProvinceId(Long provinceId);
}
