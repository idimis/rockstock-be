package com.rockstock.backend.infrastructure.usecase.warehouse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseResponseDTO {

    private Long id;
    private String name;
    private String location;
    private String latitude;
    private String longitude;
    private String contactNumber;
    private Long adminId; // Admin responsible for this warehouse
}
