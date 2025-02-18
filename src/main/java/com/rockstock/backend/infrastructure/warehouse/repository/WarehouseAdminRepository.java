package com.rockstock.backend.infrastructure.warehouse.repository;

import com.rockstock.backend.entity.warehouse.WarehouseAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseAdminRepository extends JpaRepository<WarehouseAdmin, Long> {
}
