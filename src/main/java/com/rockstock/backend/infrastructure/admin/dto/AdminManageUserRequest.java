package com.rockstock.backend.infrastructure.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminManageUserRequest {
    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String role;

    private boolean active;
}
