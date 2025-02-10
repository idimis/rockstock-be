package com.rockstock.backend.infrastructure.productCategory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductCategoryRequestDTO {
    @NotNull
    private Long categoryId;

    private String categoryName;
    private String categoryPicture;
}
