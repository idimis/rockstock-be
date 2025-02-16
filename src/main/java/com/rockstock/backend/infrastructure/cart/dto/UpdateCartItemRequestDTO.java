package com.rockstock.backend.infrastructure.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemRequestDTO {

    private BigDecimal quantity;
    private BigDecimal totalAmount;
}
