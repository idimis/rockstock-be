package com.rockstock.backend.usecase.user.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SetPasswordDTO {
    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 60, message = "Password must be between 8 and 60 characters.")
    private String password;
}
