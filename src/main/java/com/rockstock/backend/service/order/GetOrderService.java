package com.rockstock.backend.service.order;

import com.rockstock.backend.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface GetOrderService {
    List<Order> getAllOrder();
    List<Order> getAllByUserId(Long userId);
    List<Order> getAllByWarehouseId(Long warehouseId);
    List<Order> getAllByUserIdAndOrderStatusName(Long userId, String statusName);
    List<Order> getAllByWarehouseIdAndOrderStatusName(Long warehouseId, String statusName);
    List<Order> getAllByPaymentMethodName(String methodName);
    Optional<Order> getById(Long orderId);
}
