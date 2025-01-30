package com.rockstock.backend.usecase.user.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SocialLoginRequestDTO {
    @NotBlank(message = "Provider is required.")
    private String provider; // Contoh: "google", "facebook", dll.

    @NotBlank(message = "OAuth token is required.")
    private String token;
}
