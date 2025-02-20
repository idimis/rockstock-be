package com.rockstock.backend.infrastructure.order.dto;

import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderItem;
import com.rockstock.backend.entity.product.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemRequestDTO {

    @NotBlank(message = "Quantity is required")
    private BigDecimal quantity;

    @NotNull
    private Long orderId;

    @NotNull
    private Long productId;

    public OrderItem toEntity(Order order, Product product) {
        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(quantity);
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        return orderItem;
    }
}
