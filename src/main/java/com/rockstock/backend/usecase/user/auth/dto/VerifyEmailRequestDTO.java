package com.rockstock.backend.usecase.user.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyEmailRequestDTO {
    @NotBlank(message = "Verification token is required.")
    private String token;
}
