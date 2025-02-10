package com.rockstock.backend.infrastructure.admin.repository;

import com.rockstock.backend.entity.warehouse.WarehouseAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<WarehouseAdmin, Long> {
    boolean existsByUserId(Long userId);
}
