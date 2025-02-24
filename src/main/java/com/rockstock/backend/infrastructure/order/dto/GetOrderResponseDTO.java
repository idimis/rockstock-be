package com.rockstock.backend.infrastructure.order.dto;

import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderItem;
import com.rockstock.backend.entity.order.OrderStatusList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponseDTO {

    private Long orderId;
    private String orderCode;
    private String paymentProof;
    private BigDecimal deliveryCost;
    private BigDecimal totalPrice;
    private BigDecimal totalPayment;
    private OffsetDateTime createdAt;
    private Long userId;
    private String addressDetail;
    private String addressSubDistrict;
    private String warehouseName;
    private OrderStatusList orderStatus;
    private String paymentMethod;

    public GetOrderResponseDTO(Order order) {
        this.orderId = order.getId();
        this.orderCode = order.getOrderCode();
        this.paymentProof = order.getPaymentProof();
        this.deliveryCost = order.getDeliveryCost();
        this.totalPrice = order.getTotalPrice();
        this.totalPayment = order.getTotalPayment();
        this.createdAt = order.getCreatedAt();
        this.userId = order.getUser().getId();
        this.addressDetail = order.getAddress().getAddressDetail();
        this.addressSubDistrict = order.getAddress().getSubDistrict().getName();
        this.warehouseName = order.getWarehouse().getName();
        this.orderStatus = order.getOrderStatus().getStatus();
        this.paymentMethod = order.getPaymentMethod().getName();
    }
}
