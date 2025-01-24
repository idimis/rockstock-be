package com.rockstock.backend.infrastructure.usecase.user.auth.service;

import com.rockstock.backend.infrastructure.usecase.user.auth.dto.LoginRequestDTO;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.LoginResponseDTO;

public interface LoginUsecase {
    LoginResponseDTO authenticateUser(LoginRequestDTO request);
}