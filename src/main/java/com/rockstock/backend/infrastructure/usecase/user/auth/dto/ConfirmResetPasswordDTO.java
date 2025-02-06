package com.rockstock.backend.infrastructure.usecase.user.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmResetPasswordDTO {
    @NotBlank(message = "Reset token is required.")
    private String resetToken;

    @NotBlank(message = "Password is required.")
    private String newPassword;
}
