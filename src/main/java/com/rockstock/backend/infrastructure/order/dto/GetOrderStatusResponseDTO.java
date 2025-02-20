package com.rockstock.backend.infrastructure.order.dto;

import com.rockstock.backend.entity.order.OrderStatus;
import com.rockstock.backend.entity.order.OrderStatusList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderStatusResponseDTO {

    private Long orderStatusId;
    private Enum<OrderStatusList> status;
    private String detail;

    public GetOrderStatusResponseDTO(OrderStatus orderStatus) {
        this.orderStatusId = orderStatus.getId();
        this.status = orderStatus.getStatus();
        this.detail = orderStatus.getDetail();
    }
}
