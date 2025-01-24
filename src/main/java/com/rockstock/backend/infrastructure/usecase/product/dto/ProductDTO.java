package com.rockstock.backend.infrastructure.usecase.product.dto;

import com.rockstock.backend.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link Product}
 */
@Value
@Getter
@Setter
@AllArgsConstructor
public class ProductDTO implements Serializable {
    Long id;
    Long organizerId;
    @NotBlank(message = "Image URL is mandatory")
    String imageUrl;
    @NotBlank(message = "Title is mandatory")
    String title;
    @NotBlank(message = "Description is mandatory")
    String description;
    LocalDateTime dateTimeStart;
    LocalDateTime dateTimeEnd;
    @NotBlank(message = "Location is mandatory")
    String location;
    @NotBlank(message = "Location details are mandatory")
    String locationDetails;
    @NotBlank(message = "Category is mandatory")
    String category;
    BigDecimal fee;
    Integer availableSeats;
    Integer bookedSeats;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;
}
