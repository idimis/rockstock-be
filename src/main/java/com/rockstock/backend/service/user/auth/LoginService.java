package com.rockstock.backend.service.user.auth;

import com.rockstock.backend.infrastructure.user.auth.dto.LoginRequestDTO;
import com.rockstock.backend.infrastructure.user.auth.dto.TokenPairResponseDTO;

public interface LoginService {
    TokenPairResponseDTO authenticateUser(LoginRequestDTO request);
}