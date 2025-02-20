package com.rockstock.backend.infrastructure.geolocation.dto;

import com.rockstock.backend.entity.geolocation.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCityResponseDTO {

    private Long id;
    private String name;
    private Long provinceId;

    public GetCityResponseDTO(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.provinceId = city.getProvince().getId();
    }
}
