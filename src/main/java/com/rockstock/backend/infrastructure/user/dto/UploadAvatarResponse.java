package com.rockstock.backend.infrastructure.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadAvatarResponse {
    private String avatarUrl;
}
