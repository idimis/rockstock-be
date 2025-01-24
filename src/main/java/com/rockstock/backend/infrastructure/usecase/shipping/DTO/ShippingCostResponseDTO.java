package com.rockstock.backend.infrastructure.usecase.shipping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingCostResponseDTO {

    private String origin;
    private String destination;
    private double distance;
    private double cost;
    private String shippingMethod;
}
