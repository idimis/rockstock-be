package com.rockstock.backend.service.user.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface TokenGenerationService {
    enum TokenType {
        ACCESS, REFRESH
    }

    String generateToken(Authentication authentication, TokenType tokenType);
    String refreshAccessToken(String refreshToken);
}
