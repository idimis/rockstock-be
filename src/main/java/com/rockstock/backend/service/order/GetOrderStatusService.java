package com.rockstock.backend.service.order;

import com.rockstock.backend.entity.order.OrderStatus;
import com.rockstock.backend.entity.order.OrderStatusList;

import java.util.List;

public interface GetOrderStatusService {
    List<OrderStatus> getAllOrderStatus();
    OrderStatus getByOrderStatusName(OrderStatusList status);
}
