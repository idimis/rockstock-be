package com.rockstock.backend.service.geolocation;

import com.rockstock.backend.entity.geolocation.SubDistrict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GetSubDistrictService {
    List<SubDistrict> getAllSubDistricts();
    List<SubDistrict> getSubDistrictsByProvinceId(Long provinceId);
    List<SubDistrict> getSubDistrictsByProvinceName(String name);
    List<SubDistrict> getSubDistrictsByCityId(Long cityId);
    List<SubDistrict> getSubDistrictsByCityName(String name);
    List<SubDistrict> getSubDistrictsByDistrictId(Long districtId);
    List<SubDistrict> getSubDistrictsByDistrictName(String name);
    Optional<SubDistrict> getBySubDistrictId(Long subDistrictId);
    List<SubDistrict> getBySubDistrictName(String name);
}
