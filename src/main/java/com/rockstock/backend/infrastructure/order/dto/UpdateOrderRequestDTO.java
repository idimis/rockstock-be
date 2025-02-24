package com.rockstock.backend.infrastructure.order.dto;

import com.rockstock.backend.entity.order.OrderStatusList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateOrderRequestDTO {
//    private OrderStatusList newStatus;
    private MultipartFile paymentProof;
}
