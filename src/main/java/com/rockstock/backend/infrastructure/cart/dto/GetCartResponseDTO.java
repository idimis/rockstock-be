package com.rockstock.backend.infrastructure.cart.dto;

import com.rockstock.backend.entity.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponseDTO {

    private Long cartId;
    private boolean isActive;
    private Long userId;

    public GetCartResponseDTO(Cart cart) {
        this.cartId = cart.getId();
        this.isActive = cart.getIsActive();
        this.userId = cart.getUser().getId();
    }
}
