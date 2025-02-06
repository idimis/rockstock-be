package com.rockstock.backend.infrastructure.usecase.geolocation.service;

import com.rockstock.backend.infrastructure.usecase.geolocation.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository locationRepository;

    public CityService(CityRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
