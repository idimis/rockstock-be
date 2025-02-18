package com.rockstock.backend.infrastructure.address.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.infrastructure.address.dto.CreateAddressRequestDTO;
import com.rockstock.backend.infrastructure.address.dto.GetAddressResponseDTO;
import com.rockstock.backend.infrastructure.address.dto.UpdateAddressRequestDTO;
import com.rockstock.backend.service.address.CreateAddressService;
import com.rockstock.backend.service.address.DeleteAddressService;
import com.rockstock.backend.service.address.GetAddressService;
import com.rockstock.backend.service.address.UpdateAddressService;
import com.rockstock.backend.service.address.impl.GetAddressServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final CreateAddressService createAddressService;
    private final GetAddressService getAddressService;
    private final UpdateAddressService updateAddressService;
    private final DeleteAddressService deleteAddressService;

    public AddressController(
            CreateAddressService createAddressService,
            GetAddressService getAddressService,
            UpdateAddressService updateAddressService,
            DeleteAddressService deleteAddressService
    ) {
        this.createAddressService = createAddressService;
        this.getAddressService = getAddressService;
        this.updateAddressService = updateAddressService;
        this.deleteAddressService = deleteAddressService;
    }

    // Create
    @PostMapping
    public ResponseEntity<?> createAddress(@Valid @RequestBody CreateAddressRequestDTO req) {
        return ApiResponse.success(HttpStatus.OK.value(), "Create address success!", createAddressService.createAddress(req));
    }

    // Read / Get
    @GetMapping("/users")
    public ResponseEntity<?> getAddressesByUserId(@RequestParam Long userId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all user addresses success!", getAddressService.getAddressesByUserId(userId));
    }

    @GetMapping("/{addressId}/user/{userId}")
    public ResponseEntity<?> getAddressByUserIdAndAddressId(@PathVariable Long userId, @PathVariable Long addressId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get user address success!", getAddressService.getAddressByUserIdAndAddressId(userId, addressId));
    }

    @GetMapping("/user/{userId}/main")
    public ResponseEntity<?> getMainAddressByUserId(@PathVariable Long userId, @PathVariable boolean isMain) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all user addresses success!", getAddressService.getMainAddressByUserId(userId, true));
    }

    // Update
    @PutMapping("/{addressId}/user/{userId}")
    public ResponseEntity<?> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody UpdateAddressRequestDTO req
    ) {
        return ApiResponse.success(HttpStatus.OK.value(), "Update address success!", updateAddressService.updateAddress(userId, addressId, req));
    }

    // Delete
    @PutMapping("/soft-delete/{addressId}/user/{userId}")
    public ResponseEntity<?> softDeleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        return ApiResponse.success(HttpStatus.OK.value(), "Address moved to trash!", deleteAddressService.softDeleteAddress(userId, addressId));
    }

    @DeleteMapping("/hard-delete/{addressId}/user/{userId}")
    public ResponseEntity<?> hardDeleteDeletedAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        deleteAddressService.hardDeleteDeletedAddress(userId, addressId);
        return ApiResponse.success("Address permanently deleted!");
    }

    @DeleteMapping("/hard-delete/clear-all/user/{userId}")
    public ResponseEntity<?> hardDeleteAllDeletedAddresses(@PathVariable Long userId) {
        deleteAddressService.hardDeleteAllDeletedAddresses(userId);
        return ApiResponse.success("All address in trash permanently deleted!");
    }
}
