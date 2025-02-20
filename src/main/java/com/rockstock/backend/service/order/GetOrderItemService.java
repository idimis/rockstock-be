package com.rockstock.backend.service.order;

import com.rockstock.backend.entity.order.OrderItem;

import java.util.List;
import java.util.Optional;

public interface GetOrderItemService {
    List<OrderItem> getAllByOrderId(Long orderId);
    Optional<OrderItem> getByOrderIdAndProductId(Long orderId, Long productId);
    Optional<OrderItem> getByOrderIdAndId(Long orderId, Long id);
}
