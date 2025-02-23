package com.rockstock.backend.infrastructure.product.dto;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.productPicture.dto.GetProductPicturesResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductResponseDTO {
    private Long productId;
    private String productName;
    private String detail;
    private BigDecimal price;
    private BigDecimal weight;
    private BigDecimal totalStock;
    private String productCategory;
    private GetProductPicturesResponseDTO productPictures;

    public static GetAllProductResponseDTO fromProduct(Product product, BigDecimal totalStock) {
        return new GetAllProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getDetail(),
                product.getPrice(),
                product.getWeight(),
                product.getTotalStock(),
                product.getProductCategory().getCategoryName(),
                product.getProductPictures().stream()
                        .filter(picture -> picture.getPosition() == 1) // ✅ Ensure only position 1 is selected
                        .findFirst() // Get the first (position 1) picture
                        .map(GetProductPicturesResponseDTO::fromProductPicture)
                        .orElse(null) // If no picture at position 1, return null
        );
    }
}