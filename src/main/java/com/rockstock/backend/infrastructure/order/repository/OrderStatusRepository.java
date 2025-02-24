package com.rockstock.backend.infrastructure.order.repository;

import com.rockstock.backend.entity.order.OrderStatus;
import com.rockstock.backend.entity.order.OrderStatusList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    @Query("SELECT os FROM OrderStatus os WHERE os.status = :status")
    OrderStatus findByStatus(@Param("status") OrderStatusList status);


}
