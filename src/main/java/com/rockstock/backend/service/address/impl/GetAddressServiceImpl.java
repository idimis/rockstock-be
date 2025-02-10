package com.rockstock.backend.service.address.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.infrastructure.address.repository.AddressRepository;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.address.GetAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GetAddressServiceImpl implements GetAddressService {

    private final AddressRepository addressRepository;

    public GetAddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserId(Long userId) {
        List<Address> userAddresses = addressRepository.findByUserId(userId);
        if (userAddresses.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return userAddresses;
    }

    @Override
    @Transactional
    public Optional<Address> getAddressByUserIdAndAddressId(Long userId, Long addressId) {
        Optional<Address> userAddress = addressRepository.findByUserIdAndAddressId(userId, addressId);
        if (userAddress.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return userAddress;
    }

    @Override
    @Transactional
    public Optional<Address> getMainAddressByUserId(Long userId, boolean isMain) {
        Optional<Address> userMainAddress = addressRepository.findByUserIdAndIsMainTrue(userId, true);
        if (userMainAddress.isEmpty()){
            throw new DataNotFoundException("Main address not found ! or User still do not have any address !");
        }
        return userMainAddress;
    }
}
