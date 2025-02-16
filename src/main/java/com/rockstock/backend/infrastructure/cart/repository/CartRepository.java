package com.rockstock.backend.infrastructure.cart.repository;

import com.rockstock.backend.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT a FROM Cart a WHERE a.user.id = :userId")
    List<Cart> findByUserId(Long userId);

    @Query("SELECT a FROM Cart a WHERE a.user.id = :userId AND a.isActive = true")
    Optional<Cart> findActiveCartByUserId(Long userId, boolean isActive);
}
