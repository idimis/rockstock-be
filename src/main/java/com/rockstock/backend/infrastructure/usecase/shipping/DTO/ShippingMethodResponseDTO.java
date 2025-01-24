package com.rockstock.backend.infrastructure.usecase.shipping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingMethodResponseDTO {

    private String methodName;
    private double costPerKm;
}
