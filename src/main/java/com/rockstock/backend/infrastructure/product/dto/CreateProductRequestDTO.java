package com.rockstock.backend.infrastructure.product.dto;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long categoryId;

    public Product toProduct(ProductCategory productCategory) {
        Product product = new Product();
        product.setProductName(this.productName);
        product.setDetail(this.detail);
        product.setPrice(this.price);
        product.setWeight(this.weight);
        product.setProductCategory(productCategory);
        product.setTotalStock(BigDecimal.ZERO); // Automatically set total stock to 0
        return product;
    }
}