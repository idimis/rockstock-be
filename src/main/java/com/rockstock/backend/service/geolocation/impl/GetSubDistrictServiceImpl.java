package com.rockstock.backend.service.geolocation.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.District;
import com.rockstock.backend.entity.geolocation.SubDistrict;
import com.rockstock.backend.infrastructure.geolocation.repository.DistrictRepository;
import com.rockstock.backend.infrastructure.geolocation.repository.SubDistrictRepository;
import com.rockstock.backend.service.geolocation.GetDistrictService;
import com.rockstock.backend.service.geolocation.GetSubDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GetSubDistrictServiceImpl implements GetSubDistrictService {

    private final SubDistrictRepository subDistrictRepository;

    @Override
    @Transactional
    public List<SubDistrict> getAllSubDistricts() {
        return subDistrictRepository.findAll();
    }

    @Override
    @Transactional
    public List<SubDistrict> getSubDistrictsByProvinceId(Long provinceId) {
        List<SubDistrict> subDistrictsByProvinceId = subDistrictRepository.findByProvinceId(provinceId);
        if (subDistrictsByProvinceId.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrictsByProvinceId;
    }

    @Override
    @Transactional
    public List<SubDistrict> getSubDistrictsByProvinceName(String name) {
        List<SubDistrict> subDistrictsByProvinceName = subDistrictRepository.findByProvinceName(name);
        if (subDistrictsByProvinceName.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrictsByProvinceName;
    }

    @Override
    @Transactional
    public List<SubDistrict> getSubDistrictsByCityId(Long cityId) {
        List<SubDistrict> subDistrictsByCityId = subDistrictRepository.findByCityId(cityId);
        if (subDistrictsByCityId.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrictsByCityId;
    }

    @Override
    @Transactional
    public List<SubDistrict> getSubDistrictsByCityName(String name) {
        List<SubDistrict> subDistrictsByCityName = subDistrictRepository.findByCityName(name);
        if (subDistrictsByCityName.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrictsByCityName;
    }

    @Override
    @Transactional
    public List<SubDistrict> getSubDistrictsByDistrictId(Long districtId) {
        List<SubDistrict> subDistrictsByDistrictId = subDistrictRepository.findByDistrictId(districtId);
        if (subDistrictsByDistrictId.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrictsByDistrictId;
    }

    @Override
    @Transactional
    public List<SubDistrict> getSubDistrictsByDistrictName(String name) {
        List<SubDistrict> subDistrictsByDistrictName = subDistrictRepository.findByDistrictName(name);
        if (subDistrictsByDistrictName.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrictsByDistrictName;
    }

    @Override
    @Transactional
    public Optional<SubDistrict> getBySubDistrictId(Long subDistrictId) {
        Optional<SubDistrict> subDistrict = subDistrictRepository.findById(subDistrictId);
        if (subDistrict.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrict;
    }

    @Override
    @Transactional
    public List<SubDistrict> getBySubDistrictName(String name) {
        List<SubDistrict> subDistrictByName = subDistrictRepository.findByNameContainingIgnoreCase(name);
        if (subDistrictByName.isEmpty()){
            throw new DataNotFoundException("SubDistrict not found !");
        }
        return subDistrictByName;
    }
}
