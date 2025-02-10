package com.rockstock.backend.service.user.auth;

import com.rockstock.backend.infrastructure.user.auth.dto.LogoutRequestDTO;

public interface LogoutService {
    Boolean logoutUser(LogoutRequestDTO req);
}
