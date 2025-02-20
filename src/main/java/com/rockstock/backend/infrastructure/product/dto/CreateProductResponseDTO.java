package com.rockstock.backend.infrastructure.product.dto;

import com.rockstock.backend.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductResponseDTO {
    private Long productId;
    private String productName;
    private String detail;
    private BigDecimal price;
    private BigDecimal weight;
    private BigDecimal totalStock;

    public static CreateProductResponseDTO fromProduct(Product product) {
        CreateProductResponseDTO dto = new CreateProductResponseDTO();
        dto.setProductId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setDetail(product.getDetail());
        dto.setPrice(product.getPrice());
        dto.setWeight(product.getWeight());
        dto.setTotalStock(product.getTotalStock());
        return dto;
    }
}