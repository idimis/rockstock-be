package com.rockstock.backend.service.order;

import com.rockstock.backend.entity.order.OrderItem;
import com.rockstock.backend.infrastructure.order.dto.CreateOrderItemRequestDTO;

public interface CreateOrderItemService {
    OrderItem createOrderItem(CreateOrderItemRequestDTO req);
}
