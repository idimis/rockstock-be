package com.rockstock.backend.service.address.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.entity.geolocation.City;
import com.rockstock.backend.infrastructure.address.dto.UpdateAddressRequestDTO;
import com.rockstock.backend.infrastructure.address.repository.AddressRepository;
import com.rockstock.backend.infrastructure.geolocation.repository.CityRepository;
import com.rockstock.backend.service.address.UpdateAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateAddressServiceImpl implements UpdateAddressService {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    public UpdateAddressServiceImpl(AddressRepository addressRepository, CityRepository cityRepository) {
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public Address updateAddress(Long userId, Long addressId, UpdateAddressRequestDTO req) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found !"));

        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: Address does not belong to the user");
        }

        if (req.getLabel() != null) {
            existingAddress.setLabel(req.getLabel());
        }
        if (req.getAddressDetail() != null) {
            existingAddress.setAddressDetail(req.getAddressDetail() );
        }
        if (req.getLongitude() != null) {
            existingAddress.setLongitude(req.getLongitude());
        }
        if (req.getLatitude() != null) {
            existingAddress.setLatitude(req.getLatitude());
        }
        if (req.getNote() != null) {
            existingAddress.setNote(req.getNote());
        }
        if (req.getCityId() != null) {
            City city = cityRepository.findById(req.getCityId())
                    .orElseThrow(() -> new DataNotFoundException("City not found!"));
            existingAddress.setCity(city);
        }
        if (req.isMain()) {
            Optional<Address> mainAddress = addressRepository.findByUserIdAndIsMainTrue(existingAddress.getUser().getId(), true);

            if (mainAddress.isPresent()) {
                Address currentMainAddress = mainAddress.get();

                // Set the current main address to false
                currentMainAddress.setIsMain(false);
                addressRepository.save(currentMainAddress);
            }
            // Set the new address as main
            existingAddress.setIsMain(true);
        }

        return addressRepository.save(existingAddress);
    }
}
