package com.rockstock.backend.infrastructure.stockChangeType.repository;

import com.rockstock.backend.entity.stock.StockChangeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockChangeTypeRepository extends JpaRepository<StockChangeType, Long> {
    Optional<StockChangeType> findByChangeType(String changeType);
}