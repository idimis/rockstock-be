package com.rockstock.backend.service.geolocation.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.City;
import com.rockstock.backend.infrastructure.geolocation.repository.CityRepository;
import com.rockstock.backend.infrastructure.geolocation.repository.ProvinceRepository;
import com.rockstock.backend.service.geolocation.GetCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GetCityServiceImpl implements GetCityService {

    private final CityRepository cityRepository;

    @Override
    @Transactional
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    @Transactional
    public List<City> getCitiesByProvinceId(Long provinceId) {
        List<City> citiesByProvinceId = cityRepository.findByProvinceId(provinceId);
        if (citiesByProvinceId.isEmpty()){
            throw new DataNotFoundException("City not found !");
        }
        return citiesByProvinceId;
    }

    @Override
    @Transactional
    public List<City> getCitiesByProvinceName(String name) {
        List<City> citiesByProvinceName = cityRepository.findByProvinceName(name);
        if (citiesByProvinceName.isEmpty()){
            throw new DataNotFoundException("City not found !");
        }
        return citiesByProvinceName;
    }

    @Override
    @Transactional
    public Optional<City> getByCityId(Long cityId) {
        Optional<City> city = cityRepository.findById(cityId);
        if (city.isEmpty()){
            throw new DataNotFoundException("Province not found !");
        }
        return city;
    }

    @Override
    @Transactional
    public Optional<City> getByCityName(String name) {
        Optional<City> cityByName = cityRepository.findByNameContainingIgnoreCase(name);
        if (cityByName.isEmpty()){
            throw new DataNotFoundException("City not found !");
        }
        return cityByName;
    }
}
