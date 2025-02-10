package com.rockstock.backend.service.user.auth;

import com.rockstock.backend.infrastructure.user.auth.dto.TokenPairResponseDTO;

public interface TokenRefreshService {
    TokenPairResponseDTO refreshAccessToken(String refreshToken);
}
