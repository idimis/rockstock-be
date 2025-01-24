package com.rockstock.backend.infrastructure.usecase.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDTO {

    @Size(max = 100, message = "Name can have at most 100 characters")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    @Size(max = 250, message = "Photo profile URL can have at most 250 characters")
    private String photoProfileUrl;

    @Size(max = 20, message = "Phone number can have at most 20 characters")
    private String phoneNumber;

    @Size(max = 255, message = "Address can have at most 255 characters")
    private String address;
}
