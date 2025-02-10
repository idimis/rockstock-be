package com.rockstock.backend.service.user;

import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface CreateUserService {
    User createUser(CreateUserRequestDTO req);
}
