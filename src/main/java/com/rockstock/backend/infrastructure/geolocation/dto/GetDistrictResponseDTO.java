package com.rockstock.backend.infrastructure.geolocation.dto;

import com.rockstock.backend.entity.geolocation.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDistrictResponseDTO {

    private Long id;
    private String name;
    private Long cityId;
    private Long provinceId;

    public GetDistrictResponseDTO(District district) {
        this.id = district.getId();
        this.name = district.getName();
        this.cityId = district.getCity().getId();
        this.provinceId = district.getCity().getProvince().getId();
    }
}
