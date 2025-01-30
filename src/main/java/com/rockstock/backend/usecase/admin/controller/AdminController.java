package com.rockstock.backend.usecase.admin.controller;

import com.rockstock.backend.usecase.admin.DTO.AdminCreateRequestDTO;
import com.rockstock.backend.usecase.admin.DTO.AdminResponseDTO;
import com.rockstock.backend.usecase.admin.DTO.AdminUpdateRequestDTO;
import com.rockstock.backend.usecase.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminResponseDTO> createAdmin(@RequestBody AdminCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(adminService.createAdmin(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<AdminResponseDTO> updateAdmin(@PathVariable Long adminId, @RequestBody AdminUpdateRequestDTO requestDTO) {
        return ResponseEntity.ok(adminService.updateAdmin(adminId, requestDTO));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.noContent().build();
    }
}
