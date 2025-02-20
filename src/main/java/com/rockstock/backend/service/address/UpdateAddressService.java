package com.rockstock.backend.service.address;

import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.infrastructure.address.dto.UpdateAddressRequestDTO;

public interface UpdateAddressService {
    Address updateAddress(Long userId, Long addressId, UpdateAddressRequestDTO req);
}
