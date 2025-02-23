package com.rockstock.backend.infrastructure.productPicture.dto;

import com.rockstock.backend.entity.product.ProductPicture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductPicturesResponseDTO {
    private String productPictureUrl;
    private int position;

    public static GetProductPicturesResponseDTO fromProductPicture(ProductPicture productPicture) {
        return new GetProductPicturesResponseDTO(
                productPicture.getProductPictureUrl(),
                productPicture.getPosition()
        );
    }
}