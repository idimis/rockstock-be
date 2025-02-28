package com.rockstock.backend.service.geolocation;

import com.rockstock.backend.entity.geolocation.District;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GetDistrictService {
    List<District> getAllDistricts();
    List<District> getDistrictsByProvinceId(Long provinceId);
    List<District> getDistrictsByProvinceName(String name);
    List<District> getDistrictsByCityId(Long cityId);
    List<District> getDistrictsByCityName(String name);
    Optional<District> getByDistrictId(Long districtId);
    List<District> getByDistrictName(String name);
}
