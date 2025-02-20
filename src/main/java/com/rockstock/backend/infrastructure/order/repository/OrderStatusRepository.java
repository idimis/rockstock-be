package com.rockstock.backend.infrastructure.order.repository;

import com.rockstock.backend.entity.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    @Query("SELECT os FROM OrderStatus os WHERE os.status = :statusName")
    Optional<OrderStatus> findByStatusName(String statusName);
}
