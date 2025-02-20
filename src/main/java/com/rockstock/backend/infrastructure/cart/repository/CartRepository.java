package com.rockstock.backend.infrastructure.cart.repository;

import com.rockstock.backend.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.isActive = true")
    Cart findActiveCartByUserId(Long userId);
}
