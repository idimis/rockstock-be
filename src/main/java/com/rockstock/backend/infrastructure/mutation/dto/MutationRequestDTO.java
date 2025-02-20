package com.rockstock.backend.infrastructure.mutation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutationRequestDTO {
    @NotNull
    private Long productId;

    @NotNull
    private Long originWarehouseId;

    @NotNull
    private Long destinationWarehouseId;

    @NotNull
    private Long stockQuantity;
}