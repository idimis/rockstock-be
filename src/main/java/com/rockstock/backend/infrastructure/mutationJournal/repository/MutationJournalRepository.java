package com.rockstock.backend.infrastructure.mutationJournal.repository;

import com.rockstock.backend.entity.stock.MutationJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MutationJournalRepository extends JpaRepository<MutationJournal, Long> {
}