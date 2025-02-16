package com.rockstock.backend.infrastructure.cart.dto;

import com.rockstock.backend.entity.cart.Cart;
import com.rockstock.backend.entity.cart.CartItem;
import com.rockstock.backend.entity.product.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartItemRequestDTO {

    @NotNull(message = "Quantity is required")
    private BigDecimal quantity;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    @NotNull
    private Long cartId;

    @NotNull
    private Long productId;

    public CartItem toEntity(Cart cart, Product product) {
        CartItem cartItem = new CartItem();

        cartItem.setQuantity(quantity);
        cartItem.setTotalAmount(totalAmount);
        cartItem.setCart(cart);
        cartItem.setProduct(product);

        return cartItem;
    }
}
