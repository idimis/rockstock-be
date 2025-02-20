package com.rockstock.backend.service.address;

import com.rockstock.backend.entity.geolocation.Address;

import java.util.List;

public interface DeleteAddressService {
    Address softDeleteAddress(Long userId, Long addressId);
    void hardDeleteDeletedAddress(Long userId, Long addressId);
    void hardDeleteAllDeletedAddresses(Long userId);
}
