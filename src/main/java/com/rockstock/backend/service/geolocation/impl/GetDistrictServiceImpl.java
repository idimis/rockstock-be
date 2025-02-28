package com.rockstock.backend.service.geolocation.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.District;
import com.rockstock.backend.infrastructure.geolocation.repository.CityRepository;
import com.rockstock.backend.infrastructure.geolocation.repository.DistrictRepository;
import com.rockstock.backend.service.geolocation.GetDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GetDistrictServiceImpl implements GetDistrictService {

    private final DistrictRepository districtRepository;

    @Override
    @Transactional
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @Override
    @Transactional
    public List<District> getDistrictsByProvinceId(Long provinceId) {
        List<District> districtsByProvinceId = districtRepository.findByProvinceId(provinceId);
        if (districtsByProvinceId.isEmpty()){
            throw new DataNotFoundException("District not found !");
        }
        return districtsByProvinceId;
    }

    @Override
    @Transactional
    public List<District> getDistrictsByProvinceName(String name) {
        List<District> districtsByProvinceName = districtRepository.findByProvinceName(name);
        if (districtsByProvinceName.isEmpty()){
            throw new DataNotFoundException("District not found !");
        }
        return districtsByProvinceName;
    }

    @Override
    @Transactional
    public List<District> getDistrictsByCityId(Long cityId) {
        List<District> districtsByCityId = districtRepository.findByCityId(cityId);
        if (districtsByCityId.isEmpty()){
            throw new DataNotFoundException("District not found !");
        }
        return districtsByCityId;
    }

    @Override
    @Transactional
    public List<District> getDistrictsByCityName(String name) {
        List<District> districtsByCityName = districtRepository.findByCityName(name);
        if (districtsByCityName.isEmpty()){
            throw new DataNotFoundException("District not found !");
        }
        return districtsByCityName;
    }

    @Override
    @Transactional
    public Optional<District> getByDistrictId(Long districtId) {
        Optional<District> district = districtRepository.findById(districtId);
        if (district.isEmpty()){
            throw new DataNotFoundException("District not found !");
        }
        return district;
    }

    @Override
    @Transactional
    public List<District> getByDistrictName(String name) {
        List<District> districtByName = districtRepository.findByNameContainingIgnoreCase(name);
        if (districtByName.isEmpty()){
            throw new DataNotFoundException("District not found !");
        }
        return districtByName;
    }
}
