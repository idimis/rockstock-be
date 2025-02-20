package com.rockstock.backend.service.geolocation;

import com.rockstock.backend.entity.geolocation.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GetCityService {
    List<City> getAllCities();
    List<City> getCitiesByProvinceId(Long provinceId);
    List<City> getCitiesByProvinceName(String name);
    Optional<City> getByCityId(Long cityId);
    Optional<City> getByCityName(String name);
}
