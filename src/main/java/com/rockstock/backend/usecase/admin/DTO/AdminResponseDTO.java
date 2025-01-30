package com.rockstock.backend.usecase.admin.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponseDTO {

    private Long id;
    private String email;
    private String role;
}
