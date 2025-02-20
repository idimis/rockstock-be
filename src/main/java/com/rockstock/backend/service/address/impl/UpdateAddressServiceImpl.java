package com.rockstock.backend.service.address.impl;

import com.rockstock.backend.common.exceptions.AlreadyMainException;
import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.DuplicateDataException;
import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.entity.geolocation.SubDistrict;
import com.rockstock.backend.infrastructure.address.dto.UpdateAddressRequestDTO;
import com.rockstock.backend.infrastructure.address.repository.AddressRepository;
import com.rockstock.backend.infrastructure.geolocation.repository.SubDistrictRepository;
import com.rockstock.backend.service.address.UpdateAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateAddressServiceImpl implements UpdateAddressService {

    private final AddressRepository addressRepository;
    private final SubDistrictRepository subDistrictRepository;

    @Override
    @Transactional
    public Address updateAddress(Long userId, Long addressId, UpdateAddressRequestDTO req) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new DataNotFoundException("Address not found !"));

        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new DataNotFoundException("Unauthorized: Address does not belong to the user");
        }

        if (req.getLabel() != null) {
            Optional<Address> existingLabelAddress = addressRepository.findByUserIdAndLabel(userId, req.getLabel());
            if (existingLabelAddress.isPresent()) {
                throw new DuplicateDataException("Label is already exist !");
            }

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
        if (req.getSubDistrictId() != null) {
            SubDistrict subDistrict = subDistrictRepository.findById(req.getSubDistrictId())
                    .orElseThrow(() -> new DataNotFoundException("City not found!"));
            existingAddress.setSubDistrict(subDistrict);
        }

        return addressRepository.save(existingAddress);
    }

    @Override
    @Transactional
    public Address updateMainAddress(Long userId, Long addressId) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new DataNotFoundException("Address not found !"));

        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new DataNotFoundException("Unauthorized: Address does not belong to the user !");
        }

        if (existingAddress.getIsMain()) {
            throw new AlreadyMainException("This address is already a main address !");
        }

        Optional<Address> mainAddress = addressRepository.findByUserIdAndIsMainTrue(userId);

        if (mainAddress.isPresent()) {
            Address currentMainAddress = mainAddress.get();

            // Set the current main address to false
            currentMainAddress.setIsMain(false);
            addressRepository.save(currentMainAddress);
        }
        // Set the new address as main
        existingAddress.setIsMain(true);


        return addressRepository.save(existingAddress);
    }

    @Override
    @Transactional
    public Address restoreDeletedAddress(Long userId, Long addressId) {
        Address address = addressRepository.findDeletedAddressByUserIdAndAddressId(userId, addressId)
                .orElseThrow(() -> new DataNotFoundException("Deleted address not found!"));

        address.setDeletedAt(null);
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public List<Address> restoreAllDeletedAddresses(Long userId) {
        List<Address> deletedAddresses = addressRepository.findAllDeletedAddressesByUserId(userId);
        if (deletedAddresses.isEmpty()) {
            throw new DataNotFoundException("No deleted addresses found!");
        }

        deletedAddresses.forEach(address -> address.setDeletedAt(null));
        return addressRepository.saveAll(deletedAddresses);
    }
}
