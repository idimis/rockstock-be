package com.rockstock.backend.infrastructure.order.repository;

import com.rockstock.backend.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findAllByOrderId(Long orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId AND oi.product = :productId")
    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId AND oi.id = : id")
    Optional<OrderItem> findByOrderIdAndId(Long orderId, Long id);

}
