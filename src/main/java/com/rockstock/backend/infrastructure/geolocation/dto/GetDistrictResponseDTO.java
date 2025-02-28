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
    private String cityName;
    private Long provinceId;
    private String provinceName;

    public GetDistrictResponseDTO(District district) {
        this.id = district.getId();
        this.name = district.getName();
        this.cityId = district.getCity().getId();
        this.cityName = district.getCity().getName();
        this.provinceId = district.getCity().getProvince().getId();
        this.provinceName = district.getCity().getProvince().getName();
    }
}
