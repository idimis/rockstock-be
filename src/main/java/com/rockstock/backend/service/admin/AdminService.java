//package com.rockstock.backend.service.admin;
//
//import com.rockstock.backend.entity.warehouse.WarehouseAdmin;
//import com.rockstock.backend.infrastructure.admin.dto.AdminUserResponse;
//import com.rockstock.backend.infrastructure.admin.repository.AdminRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class AdminService {
//
//    private final AdminRepository adminRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
//        this.adminRepository = adminRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public AdminUserResponse createAdmin(AdminCreateRequestDTO requestDTO) {
//        if (adminRepository.existsByEmail(requestDTO.getEmail())) {
//            throw new RuntimeException("Admin with this email already exists");
//        }
//
//        WarehouseAdmin admin = new WarehouseAdmin();
//        admin.setEmail(requestDTO.getEmail());
//        admin.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
//        admin.setRole(requestDTO.getRole());
//
//        adminRepository.save(admin);
//
//        return mapToResponseDTO(admin);
//    }
//
//    public List<AdminResponseDTO> getAllAdmins() {
//        return adminRepository.findAll()
//                .stream()
//                .map(this::mapToResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    public AdminResponseDTO updateAdmin(Long adminId, AdminUpdateRequestDTO requestDTO) {
//        WarehouseAdmin admin = adminRepository.findById(adminId)
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//
//        if (requestDTO.getEmail() != null) {
//            admin.setEmail(requestDTO.getEmail());
//        }
//
//        if (requestDTO.getPassword() != null) {
//            admin.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
//        }
//
//        if (requestDTO.getRole() != null) {
//            admin.setRole(requestDTO.getRole());
//        }
//
//        adminRepository.save(admin);
//        return mapToResponseDTO(admin);
//    }
//
//    public void deleteAdmin(Long adminId) {
//        if (!adminRepository.existsById(adminId)) {
//            throw new RuntimeException("Admin not found");
//        }
//        adminRepository.deleteById(adminId);
//    }
//
//    private AdminResponseDTO mapToResponseDTO(WarehouseAdmin admin) {
//        AdminResponseDTO responseDTO = new AdminResponseDTO();
//        responseDTO.setId(admin.getId());
//        responseDTO.setEmail(admin.getEmail());
//        responseDTO.setRole(admin.getRole());
//        return responseDTO;
//    }
//}
