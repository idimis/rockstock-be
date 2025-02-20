package com.rockstock.backend.infrastructure.admin.controller;//package com.rockstock.backend.infrastructure.admin.controller;
//
//import com.rockstock.backend.infrastructure.admin.dto.AdminManageUserRequest;
//import com.rockstock.backend.service.admin.AdminService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/admin/users")
//@RequiredArgsConstructor
//@PreAuthorize("hasRole('SUPER_ADMIN')")
//public class UserAdminController {
//
//    private final AdminService userAdminService;
//
//    @GetMapping
//    public ResponseEntity<?> getAllUsers() {
//        return ResponseEntity.ok(userAdminService.getAllUsers());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getUserById(@PathVariable Long id) {
//        return ResponseEntity.ok(userAdminService.getUserById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createUser(@Valid @RequestBody AdminManageUserRequest request) {
//        return ResponseEntity.ok(userAdminService.createUser(request));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(
//            @PathVariable Long id,
//            @Valid @RequestBody AdminManageUserRequest request) {
//        return ResponseEntity.ok(userAdminService.updateUser(id, request));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        userAdminService.deleteUser(id);
//        return ResponseEntity.ok("User deleted successfully");
//    }
//}
