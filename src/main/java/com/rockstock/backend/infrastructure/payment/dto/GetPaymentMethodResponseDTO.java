package com.rockstock.backend.infrastructure.payment.dto;

import com.rockstock.backend.entity.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentMethodResponseDTO {

    private Long paymentMethodId;
    private String name;
    private Long paymentCategoryId;

    public GetPaymentMethodResponseDTO(PaymentMethod paymentMethod) {
        this.paymentMethodId = paymentMethod.getId();
        this.name = paymentMethod.getName();
        this.paymentCategoryId = paymentMethod.getPaymentCategory().getId();
    }
}
