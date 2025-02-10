package com.rockstock.backend.infrastructure.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UpdateProfileRequest {
    @NotBlank(message = "Fullname is mandatory")
    @Size(max = 100, message = "Fullname can have at most 100 characters")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    private String phoneNumber;

    private OffsetDateTime birthDate;

    @Size(max = 10, message = "Gender can have at most 10 characters")
    private String gender;

    public @NotNull(message = "Fullname is mandatory") @Size(max = 100, message = "Fullname can have at most 100 characters") String getFullname() {
        return "";
    }
}
