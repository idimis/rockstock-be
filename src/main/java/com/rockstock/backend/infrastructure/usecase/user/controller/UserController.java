package com.rockstock.backend.infrastructure.usecase.user.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.usecase.user.dto.UserPublicDetailsDTO;
import com.rockstock.backend.infrastructure.usecase.user.service.UserService;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    @GetMapping("/{userId}/details")
    public ResponseEntity<?> getUserDetails(@PathVariable @Positive Long userId,
                                            @PageableDefault(size = 10) Pageable pageable) {

        Page<UserPublicDetailsDTO> userDetailsPage = userService.getUserDetails(userId, pageable);


        if (userDetailsPage.isEmpty()) {
            return ApiResponse.notFound("User not found for ID: " + userId);
        }


        return ApiResponse.successfulResponse("Get user details success", userDetailsPage);
    }
}
