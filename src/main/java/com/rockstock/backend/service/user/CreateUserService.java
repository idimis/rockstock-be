package com.rockstock.backend.service.user;

import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.dto.CreateUserRequestDTO;

public interface CreateUserService {
    User createUser(CreateUserRequestDTO req);
}
