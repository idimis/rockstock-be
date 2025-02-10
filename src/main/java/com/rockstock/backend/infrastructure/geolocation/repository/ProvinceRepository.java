package com.rockstock.backend.infrastructure.geolocation.repository;

import com.rockstock.backend.entity.geolocation.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findByNameContainingIgnoreCase(String name);
}
