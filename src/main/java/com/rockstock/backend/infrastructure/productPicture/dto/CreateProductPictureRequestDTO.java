package com.rockstock.backend.infrastructure.productPicture.dto;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductPicture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductPictureRequestDTO {
    @NotNull
    private Long Id;

    @NotBlank
    private String productPictureUrl;

    private boolean isMain;

    @NotNull
    private Integer position;

    public ProductPicture toEntity(Product product) {
        ProductPicture productPicture = new ProductPicture();
        productPicture.setProduct(product);
        productPicture.setProductPictureUrl(productPictureUrl);
        productPicture.setIsMain(isMain);
        productPicture.setPosition(position);
        return productPicture;
    }
}
