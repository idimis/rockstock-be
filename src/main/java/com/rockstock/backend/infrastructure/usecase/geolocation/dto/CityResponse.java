package com.rockstock.backend.infrastructure.usecase.geolocation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private Long id;
    private String name;
    private String type;
    private Long provinceId;
}
