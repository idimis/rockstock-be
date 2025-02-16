package com.rockstock.backend.infrastructure.cart.dto;

import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequestDTO {

    private boolean isActive = true;

    @NotNull
    private Long userId;

    public Cart toEntity(User user) {
        Cart cart = new Cart();

        cart.setIsActive(isActive = true);
        cart.setUser(user);

        return cart;
    }
}
