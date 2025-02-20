package com.rockstock.backend.service.order;

import com.rockstock.backend.entity.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface GetOrderStatusService {
    List<OrderStatus> getAllOrderStatus();
    Optional<OrderStatus> getByOrderStatusName(String statusName);
}
