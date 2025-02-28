package com.rockstock.backend.infrastructure.geolocation.dto;

import com.rockstock.backend.entity.geolocation.District;
import com.rockstock.backend.entity.geolocation.SubDistrict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSubDistrictResponseDTO {

    private Long id;
    private String name;
    private String postalCode;
    private Long districtId;
    private String districtName;
    private Long cityId;
    private String cityName;
    private Long provinceId;
    private String provinceName;

    public GetSubDistrictResponseDTO(SubDistrict subDistrict) {
        this.id = subDistrict.getId();
        this.name = subDistrict.getName();
        this.postalCode = subDistrict.getPostalCode();
        this.districtId = subDistrict.getDistrict().getId();
        this.districtName = subDistrict.getDistrict().getName();
        this.cityId = subDistrict.getDistrict().getCity().getId();
        this.cityName = subDistrict.getDistrict().getCity().getName();
        this.provinceId = subDistrict.getDistrict().getCity().getProvince().getId();
        this.provinceName = subDistrict.getDistrict().getCity().getProvince().getName();
    }
}
