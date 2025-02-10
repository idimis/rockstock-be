package com.rockstock.backend.service.address;

import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.infrastructure.address.dto.CreateAddressRequestDTO;

public interface CreateAddressService {
    Address createAddress(CreateAddressRequestDTO req);
}
