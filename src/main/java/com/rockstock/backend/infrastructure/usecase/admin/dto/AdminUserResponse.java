package com.rockstock.backend.infrastructure.usecase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private boolean active;
}
