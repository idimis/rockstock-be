package com.rockstock.backend.infrastructure.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequestDTO {

    private String paymentProof;
    private Long orderStatusId;
}
