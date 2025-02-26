package com.rockstock.backend.infrastructure.product.dto;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductPicture;
import com.rockstock.backend.infrastructure.productPicture.dto.GetProductPicturesResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponseDTO {
    private Long productId;
    private String productName;
    private String detail;
    private BigDecimal price;
    private BigDecimal weight;
    private BigDecimal totalStock;
    private String productCategory;
    private List<GetProductPicturesResponseDTO> productPictures;

    public static GetProductResponseDTO fromProduct(Product product) {
        return new GetProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getDetail(),
                product.getPrice(),
                product.getWeight(),
                product.getTotalStock(), // Sum all stock values
                product.getProductCategory().getCategoryName(), // Assuming category has a `getCategoryName()`
                product.getProductPictures().stream()
                        .sorted(Comparator.comparing(ProductPicture::getPosition)) // Sort pictures by position
                        .map(GetProductPicturesResponseDTO::fromProductPicture) // Convert to DTO
                        .collect(Collectors.toList())
        );
    }
}