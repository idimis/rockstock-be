package com.rockstock.backend.infrastructure.system.productCategory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeProductCategoryDTO {
    private String categoryName;
    private String categoryPicture;
}
