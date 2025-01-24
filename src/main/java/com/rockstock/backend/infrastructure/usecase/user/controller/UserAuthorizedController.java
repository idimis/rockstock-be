package com.rockstock.backend.infrastructure.usecase.user.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.UpdatePasswordDTO;
import com.rockstock.backend.infrastructure.usecase.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserAuthorizedController {

    private final UserService userService;

    public UserAuthorizedController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to update password after email verification
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        Long userId = Claims.getUserIdFromJwt();
        userService.updatePassword(userId, updatePasswordDTO.getPassword());
        return ApiResponse.successfulResponse(HttpStatus.OK.value(), "Password updated successfully");
    }
}
