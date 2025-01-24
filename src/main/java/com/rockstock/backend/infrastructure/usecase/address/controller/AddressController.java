package com.rockstock.backend.infrastructure.usecase.address.controller;

import com.rockstock.backend.infrastructure.usecase.address.dto.AddressRequestDTO;
import com.rockstock.backend.infrastructure.usecase.address.dto.AddressResponseDTO;
import com.rockstock.backend.infrastructure.usecase.address.dto.UpdateAddressRequestDTO;
import com.rockstock.backend.infrastructure.usecase.address.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<AddressResponseDTO> createAddress(@PathVariable Long userId, @RequestBody AddressRequestDTO requestDTO) {
        AddressResponseDTO address = addressService.createAddress(userId, requestDTO);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long addressId, @RequestBody UpdateAddressRequestDTO requestDTO) {
        AddressResponseDTO address = addressService.updateAddress(addressId, requestDTO);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getUserAddresses(@PathVariable Long userId) {
        List<AddressResponseDTO> addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}
