package com.rockstock.backend.infrastructure.system.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductRequestDTO {
    private String imageUrl;
    private String title;
    private String description;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private String location;
    private String locationDetails;
    private String category;
    private BigDecimal fee;
    private Integer availableSeats;
}
