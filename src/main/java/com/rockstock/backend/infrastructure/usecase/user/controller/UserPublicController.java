package com.rockstock.backend.infrastructure.usecase.user.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.entity.User;
import com.rockstock.backend.infrastructure.usecase.user.dto.CreateUserRequestDTO;
import com.rockstock.backend.infrastructure.usecase.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/signup")
public class UserPublicController {
    private final UserService userService;

    public UserPublicController(UserService userService) {
        this.userService = userService;
    }

    // Register new user
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        // Create new user in the system
        User createdUser = userService.createUser(createUserRequestDTO);

        // Send verification email with token for password setup
        userService.sendVerificationEmail(createdUser);

        return ApiResponse.successfulResponse("Create new user success. A verification email has been sent.", createdUser);
    }
}
