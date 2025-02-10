package com.rockstock.backend.infrastructure.geolocation.repository;

import com.rockstock.backend.entity.geolocation.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByProvinceId(Long provinceId);
    List<City> findByNameContainingIgnoreCase(String name);
}
