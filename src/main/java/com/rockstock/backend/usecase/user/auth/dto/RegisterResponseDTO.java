package com.rockstock.backend.usecase.user.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDTO {
    private Long userId;
    private String message;
}
