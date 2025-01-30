package com.rockstock.backend.usecase.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name can have at most 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    // Password is not required at registration stage
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    @NotBlank(message = "Role is mandatory")
    @Size(max = 20, message = "Role can have at most 20 characters")
    private String role; // 'customer' or 'organizer'

    // Social login flag
    private boolean socialLogin = false;

    // Optional fields
    @Size(max = 250, message = "Photo profile URL can have at most 250 characters")
    private String photoProfileUrl;
}
