package com.rockstock.backend.infrastructure.user.dto;

import com.rockstock.backend.entity.user.Role;
import com.rockstock.backend.entity.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {
    @NotBlank
    @Size(min = 1, max = 100)
    private String fullName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    @NotNull
    private OffsetDateTime birthdate;

    @NotBlank
    private String gender;

    private boolean isAdmin = false;

    public User toEntity() {
        User user = new User();

        user.setFullname(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthdate);
        user.setGender(gender);
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);

        return user;
    }
}
