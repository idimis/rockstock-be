package com.rockstock.backend.usecase.warehouse.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseUpdateRequestDTO {

    private String name;
    private String location;
    private String latitude;
    private String longitude;
    private String contactNumber;
    private Long adminId; // Optional: change warehouse admin
}
