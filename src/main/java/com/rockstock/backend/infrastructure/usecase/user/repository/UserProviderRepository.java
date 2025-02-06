package com.rockstock.backend.infrastructure.usecase.user.repository;

import com.rockstock.backend.entity.user.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- Tambahkan ini

@Repository
public interface UserProviderRepository extends JpaRepository<UserProvider, Long> {
    Optional<UserProvider> findByProvider(String provider);
}
