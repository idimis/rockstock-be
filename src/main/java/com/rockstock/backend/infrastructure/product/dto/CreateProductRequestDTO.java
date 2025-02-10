package com.rockstock.backend.infrastructure.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDTO {
    @NotBlank
    private String productName;

    @NotBlank
    private String detail;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private Long productCategoryId;
}