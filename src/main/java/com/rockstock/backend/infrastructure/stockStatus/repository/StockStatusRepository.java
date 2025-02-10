package com.rockstock.backend.infrastructure.stockStatus.repository;

import com.rockstock.backend.entity.stock.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockStatusRepository extends JpaRepository<StockStatus, Long> {
    Optional<StockStatus> findByStatus(String status);
}