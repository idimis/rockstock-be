package com.rockstock.backend.usecase.location.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.usecase.location.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ApiResponse.successfulResponse("Get all locations success", locations);
    }
}
