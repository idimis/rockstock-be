package com.rockstock.backend.infrastructure.usecase.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateRequestDTO {

    private String email;
    private String password;
    private String role; // Optional: SUPER_ADMIN or WAREHOUSE_ADMIN
}
