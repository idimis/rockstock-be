package com.rockstock.backend.infrastructure.order.dto;

import com.rockstock.backend.entity.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderItemResponseDTO {

    private Long orderItemId;
    private BigDecimal quantity;
    private BigDecimal price;
    private Long orderId;
    private Long productId;

    public GetOrderItemResponseDTO(OrderItem orderItem) {
        this.orderItemId = orderItem.getId();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.orderId = orderItem.getOrder().getId();
        this.productId = orderItem.getProduct().getId();
    }
}
