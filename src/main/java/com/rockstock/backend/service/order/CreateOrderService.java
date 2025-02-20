package com.rockstock.backend.service.order;

import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.infrastructure.order.dto.CreateOrderRequestDTO;

public interface CreateOrderService {
    Order createOrder(CreateOrderRequestDTO req);
}
