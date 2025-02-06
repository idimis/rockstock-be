package com.rockstock.backend.infrastructure.usecase.user.auth.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.*;
import com.rockstock.backend.infrastructure.usecase.user.auth.service.AuthService;
import com.rockstock.backend.infrastructure.usecase.user.auth.service.EmailVerificationUsecase;
import com.rockstock.backend.infrastructure.usecase.user.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailVerificationUsecase emailVerificationUsecase;
    private final OAuthService oAuthService; // Service untuk OAuth

    // Login endpoint: authenticate the user based on the credentials (email/password)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO req) {
        LoginResponseDTO response = authService.authenticateUser(req);
        return ApiResponse.successfulResponse("Login successful", response);
    }

    // Register endpoint: create a new user and send a verification email
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO req) {
        authService.register(req);

        // Gunakan EmailVerificationUsecaseImpl untuk mengirim email verifikasi
        emailVerificationUsecase.sendVerificationEmail(req.getEmail());

        return ApiResponse.successfulResponse("Registration successful. Please verify your email to activate your account.");
    }

    // Verify email endpoint: validate the token from the verification email
    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        emailVerificationUsecase.verifyEmail(token);
        return ApiResponse.successfulResponse("Email verification successful. You can now log in.");
    }

    // Reset password request endpoint: send an email to reset the password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO req) {
        authService.sendResetPasswordEmail(req);
        return ApiResponse.successfulResponse("Reset password email sent. Please check your email.");
    }

    // Confirm reset password endpoint: finalize the password reset process
    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody ConfirmResetPasswordDTO req) {
        authService.resetPassword(req);
        return ApiResponse.successfulResponse("Password reset successful. You can now log in with your new password.");
    }

    // OAuth Login endpoint: handle login via OAuth (Google)
    @PostMapping("/oauth-login")
    public ResponseEntity<?> oauthLogin(@Valid @RequestBody OAuthLoginRequest req) {
        // Exchange the ID Token & Google Access Token for JWT access & refresh tokens
        OAuthLoginResponse response = oAuthService.handleOAuthLogin(req);
        return ApiResponse.successfulResponse("OAuth login successful", response);
    }
}
