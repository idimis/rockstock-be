package com.rockstock.backend.infrastructure.usecase.warehouse.repository;

import com.rockstock.backend.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findById(Long id);
    boolean existsById(Long id);
}
