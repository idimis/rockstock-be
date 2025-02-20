package com.rockstock.backend.infrastructure.payment.dto;

import com.rockstock.backend.entity.payment.PaymentCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentCategoryResponseDTO {

    private Long paymentCategoryId;
    private String name;

    public GetPaymentCategoryResponseDTO(PaymentCategory paymentCategory) {
        this.paymentCategoryId = paymentCategory.getId();
        this.name = paymentCategory.getName();
    }
}
