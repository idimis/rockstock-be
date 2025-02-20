package com.rockstock.backend.infrastructure.address.dto;

import com.rockstock.backend.entity.geolocation.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean isMain;
    private Long userId;
    private Long cityId;

    public GetAddressResponseDTO(Address address) {
        this.addressId = address.getId();
        this.label = address.getLabel();
        this.addressDetail = address.getAddressDetail();
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
        this.note = address.getNote();
        this.isMain = address.getIsMain();
        this.userId = address.getUser().getId();
        this.cityId = address.getCity().getId();
    }
}
