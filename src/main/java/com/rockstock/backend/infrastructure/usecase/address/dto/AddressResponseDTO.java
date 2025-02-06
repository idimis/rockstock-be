package com.rockstock.backend.infrastructure.usecase.address.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseDTO {

    private Long id;
    private String addressLine;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private boolean isPrimary;
}
