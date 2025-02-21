package com.rockstock.backend.infrastructure.user.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.infrastructure.user.dto.CreateUserRequestDTO;
import com.rockstock.backend.infrastructure.user.dto.UploadAvatarResponseDTO;
import com.rockstock.backend.service.user.CreateUserService;
import com.rockstock.backend.service.user.UserService;
import com.rockstock.backend.service.user.auth.EmailVerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDTO req) {
        // Buat user
        var userResponse = createUserService.createUser(req);

        // Kirim email verifikasi
        emailVerificationService.sendVerificationEmail(req.getEmail());

        // Kembalikan response
        return ApiResponse.success(HttpStatus.OK.value(), "Registration successful. Please verify your email to activate your account.", userResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        User userProfile = userService.getUserProfile();
        return ApiResponse.success(HttpStatus.OK.value(), "User profile retrieved successfully", userProfile);
    }


    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        emailVerificationService.verifyEmail(token);
        return ApiResponse.success(HttpStatus.OK.value(), "Email verification successful. You can now log in.", null);
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        Long userId = Claims.getUserIdFromJwt();
        UploadAvatarResponseDTO response = userService.uploadAvatar(userId, file);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Avatar uploaded successfully", response));
    }


    //    // Reset password request endpoint: send an email to reset the password
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO req) {
//        authService.sendResetPasswordEmail(req);
//        return ApiResponse.successfulResponse("Reset password email sent. Please check your email.");
//    }
//
//    // Confirm reset password endpoint: finalize the password reset process
//    @PostMapping("/confirm-reset-password")
//    public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody ConfirmResetPasswordDTO req) {
//        authService.resetPassword(req);
//        return ApiResponse.successfulResponse("Password reset successful. You can now log in with your new password.");
//    }
    
}
