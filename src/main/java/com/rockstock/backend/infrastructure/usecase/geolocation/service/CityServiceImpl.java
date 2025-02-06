package com.rockstock.backend.infrastructure.usecase.geolocation.service;

import com.rockstock.backend.entity.geolocation.City;
import com.rockstock.backend.entity.geolocation.Province;
import com.rockstock.backend.infrastructure.usecase.geolocation.dto.CityResponse;
import com.rockstock.backend.infrastructure.usecase.geolocation.repository.CityRepository;
import com.rockstock.backend.infrastructure.usecase.geolocation.repository.ProvinceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public List<CityResponse> getCitiesByProvince(Long provinceId) {
        return cityRepository.findByProvinceId(provinceId)
                .stream()
                .map(city -> new CityResponse(city.getId(), city.getName(), city.getType(), city.getProvince().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse getCityById(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        return new CityResponse(city.getId(), city.getName(), city.getType(), city.getProvince().getId());
    }

    @Override
    public CityResponse createCity(CreateCityRequest request) {
        Province province = provinceRepository.findById(request.getProvinceId())
                .orElseThrow(() -> new EntityNotFoundException("Province not found"));

        City city = new City();
        city.setName(request.getName());
        city.setType(request.getType());
        city.setProvince(province);

        City savedCity = cityRepository.save(city);
        return new CityResponse(savedCity.getId(), savedCity.getName(), savedCity.getType(), savedCity.getProvince().getId());
    }
}
