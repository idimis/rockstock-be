package com.rockstock.backend.infrastructure.usecase.user.repository;

import com.rockstock.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Existing methods
    Optional<User> findByEmailContainsIgnoreCase(String email);
    Optional<User> findByReferralCode(String referralCode);
    boolean existsByEmail(String email);

    // New methods for email verification and social login
    Optional<User> findByEmailAndIsVerifiedTrue(String email); // Check verified email
    Optional<User> findByEmailAndSocialLoginTrue(String email); // For social login users
    Optional<User> findByIdAndIsVerifiedTrue(Long userId); // Find verified user by ID
}
