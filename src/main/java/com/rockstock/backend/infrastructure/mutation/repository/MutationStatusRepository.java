package com.rockstock.backend.infrastructure.mutation.repository;

import com.rockstock.backend.entity.stock.MutationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MutationStatusRepository extends JpaRepository<MutationStatus, Long> {
    Optional<MutationStatus> findByStatus(String status);
}