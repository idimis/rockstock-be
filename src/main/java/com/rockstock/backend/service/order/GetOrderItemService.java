package com.rockstock.backend.service.order;

import com.rockstock.backend.entity.order.OrderItem;
import com.rockstock.backend.infrastructure.order.dto.GetOrderItemResponseDTO;

import java.util.List;
import java.util.Optional;

public interface GetOrderItemService {
    List<GetOrderItemResponseDTO> getFilteredOrderItems(Long orderId, String orderCode, String productName);
    Optional<GetOrderItemResponseDTO> getByIdAndOrderId(Long id, Long orderId);
}
