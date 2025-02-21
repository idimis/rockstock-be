package com.rockstock.backend.infrastructure.user.auth.controller;

import com.rockstock.backend.service.user.auth.*;
//import com.rockstock.backend.service.user.auth.AuthService;
//import com.rockstock.backend.service.user.auth.EmailVerificationService;
import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.infrastructure.user.auth.dto.*;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import jakarta.validation.Valid;
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
    private OAuthService oAuthService = null;

    public AuthController(LoginService loginService, TokenRefreshService tokenRefreshService,
                          TokenBlacklistService tokenBlacklistService, LogoutService logoutService) {
        this.loginService = loginService;
        this.tokenRefreshService = tokenRefreshService;
        this.logoutService = logoutService;
        this.oAuthService = oAuthService;

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

    @PostMapping("/oauth-login")
    public ResponseEntity<?> oauthLogin(@Valid @RequestBody OAuthLoginRequest req) {
        var response = oAuthService.handleOAuthLogin(req);
        return ApiResponse.success("OAuth login successful", response);
    }
    @PostMapping("/oauth-register")
    public ResponseEntity<?> oauthRegister(@Valid @RequestBody OAuthRegisterRequest req) {
        var response = oAuthService.handleOAuthRegister(req);
        return ApiResponse.success("OAuth register successful", response);
    }

}


