package com.rockstock.backend.service.user.impl;

import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.infrastructure.user.dto.ChangePasswordRequest;
import com.rockstock.backend.infrastructure.user.dto.UpdateProfileRequest;
import com.rockstock.backend.infrastructure.user.dto.UploadAvatarResponse;
import com.rockstock.backend.infrastructure.user.dto.UserProfileResponse;
import com.rockstock.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(user.getId(), user.getFullname(), user.getEmail(),
                user.getPhotoProfileUrl(), user.getBirthDate(), user.getGender(), user.getIsVerified());
    }

    @Override
    public void updateUserProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullname(request.getFullname());
        user.setBirthDate(request.getBirthDate());
        user.setGender(request.getGender());

        userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPassword() == null) {
            throw new RuntimeException("Password change not allowed for social login users");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UploadAvatarResponse uploadAvatar(Long userId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif"))) {
            throw new RuntimeException("Invalid file format. Allowed formats: .jpg, .jpeg, .png, .gif");
        }

        if (file.getSize() > 1024 * 1024) {
            throw new RuntimeException("File size exceeds 1MB limit");
        }

        // TODO: Implement file storage logic (S3, Cloudinary, Local Storage)
        String uploadedUrl = "https://example.com/avatar/" + userId; // Placeholder

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPhotoProfileUrl(uploadedUrl);
        userRepository.save(user);

        return new UploadAvatarResponse(uploadedUrl);
    }

    @Override
    public void updateEmail(Long userId, String newEmail) {
        if (userRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email is already in use");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(newEmail);
        user.setIsVerified(false);
        userRepository.save(user);

        // TODO: Send verification email with expiration of 1 hour
    }

    @Override
    public void resendEmailVerification(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getIsVerified()) {
            throw new RuntimeException("User is already verified");
        }

        // TODO: Implement email verification resend logic
    }
}
