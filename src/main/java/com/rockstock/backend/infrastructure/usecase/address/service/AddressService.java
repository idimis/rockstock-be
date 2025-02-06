package com.rockstock.backend.infrastructure.usecase.address.service;

import com.rockstock.backend.entity.user.Address;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.usecase.address.dto.AddressRequestDTO;
import com.rockstock.backend.infrastructure.usecase.address.dto.AddressResponseDTO;
import com.rockstock.backend.infrastructure.usecase.address.dto.UpdateAddressRequestDTO;
import com.rockstock.backend.infrastructure.usecase.address.repository.AddressRepository;
import com.rockstock.backend.infrastructure.usecase.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AddressResponseDTO createAddress(Long userId, AddressRequestDTO addressRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (addressRequestDTO.isPrimary()) {
            clearPrimaryAddress(userId);
        }

        Address address = new Address();
        address.setAddressLine(addressRequestDTO.getAddressLine());
        address.setCity(addressRequestDTO.getCity());
        address.setProvince(addressRequestDTO.getProvince());
        address.setPostalCode(addressRequestDTO.getPostalCode());
        address.setCountry(addressRequestDTO.getCountry());
        address.setPrimary(addressRequestDTO.isPrimary());
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        return mapToResponseDTO(savedAddress);
    }

    @Transactional
    public AddressResponseDTO updateAddress(Long addressId, UpdateAddressRequestDTO updateRequestDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (updateRequestDTO.isPrimary()) {
            clearPrimaryAddress(address.getUser().getId());
        }

        address.setAddressLine(updateRequestDTO.getAddressLine() != null ? updateRequestDTO.getAddressLine() : address.getAddressLine());
        address.setCity(updateRequestDTO.getCity() != null ? updateRequestDTO.getCity() : address.getCity());
        address.setProvince(updateRequestDTO.getProvince() != null ? updateRequestDTO.getProvince() : address.getProvince());
        address.setPostalCode(updateRequestDTO.getPostalCode() != null ? updateRequestDTO.getPostalCode() : address.getPostalCode());
        address.setCountry(updateRequestDTO.getCountry() != null ? updateRequestDTO.getCountry() : address.getCountry());
        address.setPrimary(updateRequestDTO.isPrimary());

        Address updatedAddress = addressRepository.save(address);
        return mapToResponseDTO(updatedAddress);
    }

    public List<AddressResponseDTO> getUserAddresses(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }

    private void clearPrimaryAddress(Long userId) {
        Address primaryAddress = addressRepository.findByUserIdAndIsPrimaryTrue(userId);
        if (primaryAddress != null) {
            primaryAddress.setPrimary(false);
            addressRepository.save(primaryAddress);
        }
    }

    private AddressResponseDTO mapToResponseDTO(Address address) {
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setId(address.getId());
        responseDTO.setAddressLine(address.getAddressLine());
        responseDTO.setCity(address.getCity());
        responseDTO.setProvince(address.getProvince());
        responseDTO.setPostalCode(address.getPostalCode());
        responseDTO.setCountry(address.getCountry());
        responseDTO.setPrimary(address.isPrimary());
        return responseDTO;
    }
}
