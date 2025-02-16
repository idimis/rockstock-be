package com.rockstock.backend.infrastructure.cart.repository;

import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.entity.geolocation.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT a FROM CartItem a WHERE a.cart.id = :cartId AND a.cart.isActive = true")
    List<CartItem> findAllByActiveCartAndCartId(Long cartId, boolean isActive);

    @Query("SELECT a FROM CartItem a WHERE a.cart.id = :cartId AND a.product = :productId AND a.cart.isActive = true")
    Optional<CartItem> findByActiveCartAndCartIdAndProductId(Long cartId, Long productId, boolean isActive);

    @Query("SELECT a FROM CartItem a WHERE a.cart.id = :cartId AND a.id = : cartItemId")
    Optional<CartItem> findByIdAndCartId(Long cartId, Long cartItemId);
}
