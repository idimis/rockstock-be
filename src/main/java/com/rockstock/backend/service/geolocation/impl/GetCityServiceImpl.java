package com.rockstock.backend.service.geolocation.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.City;
import com.rockstock.backend.infrastructure.geolocation.dto.GetCityResponseDTO;
import com.rockstock.backend.infrastructure.geolocation.repository.CityRepository;
import com.rockstock.backend.infrastructure.geolocation.repository.ProvinceRepository;
import com.rockstock.backend.service.geolocation.GetCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GetCityServiceImpl implements GetCityService {
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public List<GetCityResponseDTO> getCitiesByProvince(Long provinceId) {
        return cityRepository.findByProvinceId(provinceId)
                .stream()
                .map(city -> new GetCityResponseDTO(city.getId(), city.getName(), city.getType(), city.getProvince().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetCityResponseDTO> getCityByName(String name) {
        List<City> foundCities = cityRepository.findByNameContainingIgnoreCase(name);
        if (foundCities.isEmpty()){
            throw new DataNotFoundException("City(s) with query " + name + " not found !");
        }
        return foundCities.stream().map(GetCityResponseDTO::new).toList();
    }

    @Override
    public List<GetCityResponseDTO> getAllCities() {
        return cityRepository.findAll().stream().map(GetCityResponseDTO::new).toList();
    }

}
