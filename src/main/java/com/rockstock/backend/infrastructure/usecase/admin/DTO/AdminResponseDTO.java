package com.rockstock.backend.infrastructure.usecase.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponseDTO {

    private Long id;
    private String email;
    private String role;
}
