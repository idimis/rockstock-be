package com.rockstock.backend.infrastructure.usecase.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseCreateRequestDTO {

    @NotBlank(message = "Warehouse name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Latitude is required")
    private String latitude;

    @NotBlank(message = "Longitude is required")
    private String longitude;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    private Long adminId; // Admin responsible for this warehouse
}
