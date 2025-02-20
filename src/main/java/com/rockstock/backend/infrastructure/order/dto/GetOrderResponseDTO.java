package com.rockstock.backend.infrastructure.order.dto;

import com.rockstock.backend.entity.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponseDTO {

    private Long orderId;
    private String paymentProof;
    private BigDecimal deliveryCost;
    private BigDecimal totalPrice;
    private BigDecimal totalPayment;
    private Long userId;
    private Long addressId;
    private Long warehouseId;
    private Long orderStatusId;
    private Long paymentMethodId;

    public GetOrderResponseDTO(Order order) {
        this.orderId = order.getId();
        this.paymentProof = order.getPaymentProof();
        this.deliveryCost = order.getDeliveryCost();
        this.totalPrice = order.getTotalPrice();
        this.totalPayment = order.getTotalPayment();
        this.userId = order.getUser().getId();
        this.addressId = order.getAddress().getId();
        this.warehouseId = order.getWarehouse().getId();
        this.orderStatusId = order.getOrderStatus().getId();
        this.paymentMethodId = order.getPaymentMethod().getId();
    }
}
