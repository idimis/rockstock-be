package com.rockstock.backend.infrastructure.user.auth.controller;

//import com.rockstock.backend.service.user.auth.OAuthService;
//import com.rockstock.backend.service.user.auth.AuthService;
//import com.rockstock.backend.service.user.auth.EmailVerificationService;
import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.user.auth.dto.*;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.user.auth.LoginService;
import com.rockstock.backend.service.user.auth.LogoutService;
import com.rockstock.backend.service.user.auth.TokenRefreshService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginService loginService;
    private final TokenRefreshService tokenRefreshService;
    private final LogoutService logoutService;

    public AuthController(LoginService loginService, TokenRefreshService tokenRefreshService,
                          com.rockstock.backend.service.user.auth.TokenBlacklistService tokenBlacklistService, LogoutService logoutService) {
        this.loginService = loginService;
        this.tokenRefreshService = tokenRefreshService;
        this.logoutService = logoutService;

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDTO req) {
        return ApiResponse.success("Login successful", loginService.authenticateUser(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated @RequestBody LogoutRequestDTO req) {
        var accessToken = Claims.getJwtTokenString();
        req.setAccessToken(accessToken);
        return ApiResponse.success("Logout successful", logoutService.logoutUser(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        String tokenType = Claims.getTokenTypeFromJwt();
        if (!"REFRESH".equals(tokenType)) {
            return ApiResponse.failed(HttpStatus.UNAUTHORIZED.value(), "Invalid token type for refresh");
        }
        String token = Claims.getJwtTokenString();
        return ApiResponse.success("Refresh successful", tokenRefreshService.refreshAccessToken(token));
    }

//    // Register endpoint: create a new user and send a verification email
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO req) {
//        authService.register(req);
//
//        // Gunakan EmailVerificationServiceImpl untuk mengirim email verifikasi
//        emailVerificationService.sendVerificationEmail(req.getEmail());
//
//        return ApiResponse.successfulResponse("Registration successful. Please verify your email to activate your account.");
//    }
//
//    // Verify email endpoint: validate the token from the verification email
//    @GetMapping("/verify-email")
//    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
//        emailVerificationService.verifyEmail(token);
//        return ApiResponse.successfulResponse("Email verification successful. You can now log in.");
//    }
//
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
//
//    // OAuth Login endpoint: handle login via OAuth (Google)
//    @PostMapping("/oauth-login")
//    public ResponseEntity<?> oauthLogin(@Valid @RequestBody OAuthLoginRequest req) {
//        // Exchange the ID Token & Google Access Token for JWT access & refresh tokens
//        OAuthLoginResponse response = oAuthService.handleOAuthLogin(req);
//        return ApiResponse.successfulResponse("OAuth login successful", response);
//    }
}
