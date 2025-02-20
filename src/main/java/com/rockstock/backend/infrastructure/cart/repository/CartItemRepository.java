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

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.cart.isActive = true")
    List<CartItem> findAllByActiveCartId(Long cartId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId AND ci.cart.isActive = true")
    Optional<CartItem> findByActiveCartIdAndProductId(Long cartId, Long productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.productName = :name AND ci.cart.isActive = true")
    Optional<CartItem> findByActiveCartIdAndProductName(Long cartId, String name);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.id = : cartItemId")
    Optional<CartItem> findByIdAndCartId(Long cartItemId, Long cartId);
}
