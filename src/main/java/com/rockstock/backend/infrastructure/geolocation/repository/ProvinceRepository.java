package com.rockstock.backend.infrastructure.geolocation.repository;

import com.rockstock.backend.entity.geolocation.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    @Query("SELECT p FROM Province p WHERE p.name = :name")
    Optional<Province> findByNameContainingIgnoreCase(String name);
}
