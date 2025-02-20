package com.rockstock.backend.service.address;

import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.infrastructure.address.dto.UpdateAddressRequestDTO;

import java.util.List;

public interface UpdateAddressService {
    Address updateAddress(Long userId, Long addressId, UpdateAddressRequestDTO req);
    Address updateMainAddress(Long userId, Long addressId);
    Address restoreDeletedAddress(Long userId, Long addressId);
    List<Address> restoreAllDeletedAddresses(Long userId);
}
