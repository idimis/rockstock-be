package com.rockstock.backend.service.address;

import com.rockstock.backend.entity.geolocation.Address;

import java.util.List;
import java.util.Optional;

public interface GetAddressService {
    List<Address> getAddressesByUserId(Long userId);
    Optional<Address> getAddressByUserIdAndAddressId(Long userId, Long addressId);
    Optional<Address> getMainAddressByUserId(Long userId, boolean isMain);
}
