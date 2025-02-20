package com.rockstock.backend.service.address.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.DuplicateDataException;
import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.entity.geolocation.SubDistrict;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.address.dto.CreateAddressRequestDTO;
import com.rockstock.backend.infrastructure.address.repository.AddressRepository;
import com.rockstock.backend.infrastructure.geolocation.repository.SubDistrictRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.address.CreateAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateAddressServiceImpl implements CreateAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final SubDistrictRepository subDistrictRepository;

    @Override
    @Transactional
    public Address createAddress(CreateAddressRequestDTO req) {

        Long userId = Claims.getUserIdFromJwt();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        SubDistrict subDistrict = subDistrictRepository.findById(req.getSubDistrictId())
                .orElseThrow(() -> new DataNotFoundException("Sub-District not found"));

        Optional<Address> existingLabelAddress = addressRepository.findByUserIdAndLabel(userId, req.getLabel());
        if (existingLabelAddress.isPresent()) {
            throw new DuplicateDataException("Label is already exist !");
        }

        Address newAddress = req.toEntity(user, subDistrict);

        List<Address> checkAddress = addressRepository.findByUserId(userId);
        newAddress.setIsMain(checkAddress.isEmpty());

        return addressRepository.save(newAddress);
    }

}
