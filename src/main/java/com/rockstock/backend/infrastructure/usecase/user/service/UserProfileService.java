package com.rockstock.backend.infrastructure.usecase.user.service;

import com.rockstock.backend.infrastructure.usecase.user.dto.ChangePasswordRequest;
import com.rockstock.backend.infrastructure.usecase.user.dto.UpdateProfileRequest;
import com.rockstock.backend.infrastructure.usecase.user.dto.UploadAvatarResponse;
import com.rockstock.backend.infrastructure.usecase.user.dto.UserProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {
    UserProfileResponse getUserProfile(Long userId);
    void updateUserProfile(Long userId, UpdateProfileRequest request);
    void changePassword(Long userId, ChangePasswordRequest request);
    UploadAvatarResponse uploadAvatar(Long userId, MultipartFile file);
    void updateEmail(Long userId, String newEmail);
    void resendEmailVerification(Long userId);
}
