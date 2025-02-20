package com.rockstock.backend.infrastructure.address.dto;

import com.rockstock.backend.entity.geolocation.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressResponseDTO {

    private Long addressId;
    private String label;
    private String addressDetail;
    private String longitude;
    private String latitude;
    private String note;
    private Boolean isMain;
    private Long userId;
    private Long subDistrictId;
    private Long districtId;
    private Long cityId;
    private Long provinceId;

    public GetAddressResponseDTO(Address address) {
        this.addressId = address.getId();
        this.label = address.getLabel();
        this.addressDetail = address.getAddressDetail();
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
        this.note = address.getNote();
        this.isMain = address.getIsMain();
        this.userId = address.getUser().getId();
        this.subDistrictId = address.getSubDistrict().getId();
        this.districtId = address.getSubDistrict().getDistrict().getId();
        this.cityId = address.getSubDistrict().getDistrict().getCity().getId();
        this.provinceId = address.getSubDistrict().getDistrict().getCity().getProvince().getId();
    }
}
