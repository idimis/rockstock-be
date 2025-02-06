package com.rockstock.backend.infrastructure.usecase.user.auth.service;

import com.rockstock.backend.infrastructure.usecase.user.auth.dto.OAuthLoginRequest;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.OAuthLoginResponse;
import com.rockstock.backend.infrastructure.system.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final TokenService tokenService; // Untuk mengelola token JWT
    private final UserService userService;   // Service untuk mengelola data user (cek email, verifikasi, dll)

    // Method untuk menangani login via OAuth (ID Token & Google Access Token)
    public OAuthLoginResponse handleOAuthLogin(OAuthLoginRequest request) {
        // 1. Validasi ID Token dan Google Access Token (opsional jika perlu validasi lebih lanjut)
        // Misalnya, verifikasi token menggunakan Google API untuk mendapatkan user info

        // 2. Ambil informasi user dari Google berdasarkan ID Token & Access Token
        OAuthUserInfo googleUser = fetchUserInfoFromGoogle(request.getIdToken(), request.getAccessToken());

        // 3. Cek apakah user sudah ada, jika belum maka buat user baru
        String email = googleUser.getEmail();
        if (!userService.isEmailExists(email)) {
            userService.registerUser(googleUser);
        }

        // 4. Generate JWT Token untuk user yang sudah ada atau baru
        String accessToken = tokenService.generateToken(
                Map.of("email", email, "role", "USER"), // Ganti dengan role atau claim yang sesuai
                email,
                3600000L // Set expiration dalam milidetik (misalnya 1 jam)
        );
        String refreshToken = tokenService.generateToken(
                Map.of("email", email),
                email,
                86400000L // Set expiration refresh token (misalnya 24 jam)
        );

        // 5. Kembalikan response yang berisi JWT tokens
        return new OAuthLoginResponse(accessToken, refreshToken);
    }

    // Method untuk mengambil data user dari Google
    private OAuthUserInfo fetchUserInfoFromGoogle(String idToken, String accessToken) {
        // Implementasikan panggilan ke Google API untuk mengambil user info
        // dengan ID Token dan Access Token
        // Misalnya, panggil endpoint userinfo dari Google untuk mendapatkan data user
        // Pastikan Anda menggunakan Google API client untuk memverifikasi token

        return new OAuthUserInfo("user@example.com", "John Doe"); // Contoh mock response
    }
}
