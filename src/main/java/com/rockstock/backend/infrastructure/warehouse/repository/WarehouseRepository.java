package com.rockstock.backend.infrastructure.warehouse.repository;

import com.rockstock.backend.entity.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
