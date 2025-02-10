package com.rockstock.backend.infrastructure.stockJournal.repository;

import com.rockstock.backend.entity.stock.StockJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJournalRepository extends JpaRepository<StockJournal, Long> {
}