package com.rockstock.backend.infrastructure.usecase.user.auth.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.*;
import com.rockstock.backend.infrastructure.usecase.user.auth.service.AuthService;
import com.rockstock.backend.infrastructure.usecase.user.auth.service.EmailVerificationService; // Import service baru
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailVerificationService emailVerificationService; // Dependency injection untuk EmailVerificationService

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO req) {
        LoginResponseDTO response = authService.authenticateUser(req);
        return ApiResponse.successfulResponse("Login successful", response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO req) {
        authService.register(req);

        // Setelah registrasi sukses, kirimkan email verifikasi
        emailVerificationService.sendVerificationEmail(req.getEmail());

        return ApiResponse.successfulResponse("Registration successful. Please verify your email to activate your account.");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        authService.verifyEmail(token);
        return ApiResponse.successfulResponse("Email verification successful. You can now log in.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO req) {
        authService.sendResetPasswordEmail(req);
        return ApiResponse.successfulResponse("Reset password email sent. Please check your email.");
    }

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody ConfirmResetPasswordDTO req) {
        authService.resetPassword(req);
        return ApiResponse.successfulResponse("Password reset successful. You can now log in with your new password.");
    }
}
