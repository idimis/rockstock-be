package com.rockstock.backend.infrastructure.usecase.user.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;
}
