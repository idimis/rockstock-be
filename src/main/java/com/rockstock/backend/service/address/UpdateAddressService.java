package com.rockstock.backend.service.address;

import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.infrastructure.address.dto.UpdateAddressRequestDTO;

import java.util.List;
import java.util.Optional;

public interface UpdateAddressService {
    Address updateAddress(Long userId, Long addressId, UpdateAddressRequestDTO req);
}
