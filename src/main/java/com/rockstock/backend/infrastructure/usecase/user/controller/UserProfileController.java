package com.rockstock.backend.controller.user;

import com.rockstock.backend.infrastructure.usecase.user.dto.UpdateProfileRequest;
import com.rockstock.backend.infrastructure.usecase.user.service.UserProfileService;
//import com.rockstock.backend.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;



    @GetMapping
    public ResponseEntity<?> getUserProfile(Principal principal) {
        return ResponseEntity.ok(userProfileService.getUserProfile(principal.getName()));
    }

    @PostMapping("/resend-verification-email")
    public ResponseEntity<?> resendVerificationEmail(Principal principal) {
        userProfileService.resendVerificationEmail(principal.getName());
        return ResponseEntity.ok("Verification email resent successfully");
    }



    @PutMapping
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            Principal principal) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(principal.getName(), request));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Principal principal) {
        userProfileService.changeUserPassword(principal.getName(), oldPassword, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            Principal principal) {
        return ResponseEntity.ok(userProfileService.uploadUserAvatar(principal.getName(), file));
    }
}
