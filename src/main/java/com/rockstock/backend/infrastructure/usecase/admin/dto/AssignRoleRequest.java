package com.rockstock.backend.infrastructure.usecase.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignRoleRequest {
    private Long userId;

    @NotBlank
    private String newRole;
}
