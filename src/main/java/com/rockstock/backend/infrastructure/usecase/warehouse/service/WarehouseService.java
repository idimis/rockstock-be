package com.rockstock.backend.infrastructure.usecase.warehouse.service;

import com.rockstock.backend.entity.Warehouse;
import com.rockstock.backend.entity.Admin;
import com.rockstock.backend.infrastructure.usecase.warehouse.dto.WarehouseCreateRequestDTO;
import com.rockstock.backend.infrastructure.usecase.warehouse.dto.WarehouseResponseDTO;
import com.rockstock.backend.infrastructure.usecase.warehouse.dto.WarehouseUpdateRequestDTO;
import com.rockstock.backend.infrastructure.usecase.warehouse.repository.WarehouseRepository;
import com.rockstock.backend.infrastructure.usecase.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final AdminRepository adminRepository;

    public WarehouseService(WarehouseRepository warehouseRepository, AdminRepository adminRepository) {
        this.warehouseRepository = warehouseRepository;
        this.adminRepository = adminRepository;
    }

    public WarehouseResponseDTO createWarehouse(WarehouseCreateRequestDTO requestDTO) {
        Admin admin = adminRepository.findById(requestDTO.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Warehouse warehouse = new Warehouse();
        warehouse.setName(requestDTO.getName());
        warehouse.setLocation(requestDTO.getLocation());
        warehouse.setLatitude(requestDTO.getLatitude());
        warehouse.setLongitude(requestDTO.getLongitude());
        warehouse.setContactNumber(requestDTO.getContactNumber());
        warehouse.setAdmin(admin);

        warehouseRepository.save(warehouse);
        return mapToResponseDTO(warehouse);
    }

    public List<WarehouseResponseDTO> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public WarehouseResponseDTO updateWarehouse(Long warehouseId, WarehouseUpdateRequestDTO requestDTO) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        if (requestDTO.getName() != null) {
            warehouse.setName(requestDTO.getName());
        }
        if (requestDTO.getLocation() != null) {
            warehouse.setLocation(requestDTO.getLocation());
        }
        if (requestDTO.getLatitude() != null) {
            warehouse.setLatitude(requestDTO.getLatitude());
        }
        if (requestDTO.getLongitude() != null) {
            warehouse.setLongitude(requestDTO.getLongitude());
        }
        if (requestDTO.getContactNumber() != null) {
            warehouse.setContactNumber(requestDTO.getContactNumber());
        }
        if (requestDTO.getAdminId() != null) {
            Admin admin = adminRepository.findById(requestDTO.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
            warehouse.setAdmin(admin);
        }

        warehouseRepository.save(warehouse);
        return mapToResponseDTO(warehouse);
    }

    public void deleteWarehouse(Long warehouseId) {
        if (!warehouseRepository.existsById(warehouseId)) {
            throw new RuntimeException("Warehouse not found");
        }
        warehouseRepository.deleteById(warehouseId);
    }

    public void assignWarehouseAdmin(Long warehouseId, Long adminId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        warehouse.setAdmin(admin);  // Assign the new admin to the warehouse
        warehouseRepository.save(warehouse);
    }

    private WarehouseResponseDTO mapToResponseDTO(Warehouse warehouse) {
        WarehouseResponseDTO responseDTO = new WarehouseResponseDTO();
        responseDTO.setId(warehouse.getId());
        responseDTO.setName(warehouse.getName());
        responseDTO.setLocation(warehouse.getLocation());
        responseDTO.setLatitude(warehouse.getLatitude());
        responseDTO.setLongitude(warehouse.getLongitude());
        responseDTO.setContactNumber(warehouse.getContactNumber());
        responseDTO.setAdminId(warehouse.getAdmin().getId());
        return responseDTO;
    }
}
