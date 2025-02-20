package com.rockstock.backend.infrastructure.order.repository;

import com.rockstock.backend.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findAllByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE o.warehouse.id = :warehouseId")
    List<Order> findAllByWarehouseId(Long warehouseId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.orderStatus.status = :statusName")
    List<Order> findAllByUserIdAndOrderStatusName(Long userId, String statusName);

    @Query("SELECT o FROM Order o WHERE o.warehouse.id = :warehouseId AND o.orderStatus.status = :statusName")
    List<Order> findAllByWarehouseIdAndOrderStatusName(Long warehouseId, String statusName);

    @Query("SELECT o FROM Order o WHERE o.paymentMethod.name = :methodName")
    List<Order> findAllByPaymentMethodName(String methodName);
}
