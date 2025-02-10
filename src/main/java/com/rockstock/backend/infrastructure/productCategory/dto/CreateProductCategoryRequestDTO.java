package com.rockstock.backend.infrastructure.productCategory.dto;

import com.rockstock.backend.entity.product.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCategoryRequestDTO {
    @NotBlank
    private String categoryName;

    @NotBlank
    private String categoryPicture;

    public ProductCategory toEntity() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(categoryName);
        productCategory.setCategoryPicture(categoryPicture);
        return productCategory;
    }
}
