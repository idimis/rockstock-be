package com.rockstock.backend.service.warehouse;

import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.entity.warehouse.Warehouse;
import com.rockstock.backend.entity.warehouse.WarehouseAdmin;
import com.rockstock.backend.infrastructure.warehouse.dto.AssignWarehouseAdminDTO;
import com.rockstock.backend.infrastructure.warehouse.repository.WarehouseAdminRepository;
import com.rockstock.backend.infrastructure.warehouse.repository.WarehouseRepository;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseAdminServiceImpl implements WarehouseAdminService {

    private final WarehouseAdminRepository warehouseAdminRepository;
    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void assignWarehouseAdmin(AssignWarehouseAdminDTO request) {
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        WarehouseAdmin warehouseAdmin = new WarehouseAdmin();
        warehouseAdmin.setWarehouse(warehouse);
        warehouseAdmin.setUser(user);

        warehouseAdminRepository.save(warehouseAdmin);
    }

    @Transactional
    @Override
    public void removeWarehouseAdmin(Long warehouseAdminId) {
        WarehouseAdmin warehouseAdmin = warehouseAdminRepository.findById(warehouseAdminId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse Admin not found"));

        warehouseAdminRepository.delete(warehouseAdmin);
    }
}
