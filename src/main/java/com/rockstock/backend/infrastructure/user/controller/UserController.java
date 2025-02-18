package com.rockstock.backend.infrastructure.user.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.user.dto.CreateUserRequestDTO;
import com.rockstock.backend.service.user.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final CreateUserService createUserService;

    public UserController(
            CreateUserService createUserService
    ) {
        this.createUserService = createUserService;
    }

    // Create
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDTO req) {
        return ApiResponse.success(HttpStatus.OK.value(), "Create user success", createUserService.createUser(req));
    }

//    PUT UPLOAD PROFILE PICTURE
//    @PostMapping("/register")
//    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDTO req) {
//        return ApiResponse.success(HttpStatus.OK.value(), "Create user success", createUserService.createUser(req));
//    }
}
