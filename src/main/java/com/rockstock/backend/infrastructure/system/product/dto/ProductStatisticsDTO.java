package com.rockstock.backend.infrastructure.system.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductRequestDTO {
    private String imageUrl;
    private String title;
    private String description;
    private String category;
    private BigDecimal fee;
    private Integer availableStock;  // Mengganti availableSeats menjadi availableStock untuk produk
}
