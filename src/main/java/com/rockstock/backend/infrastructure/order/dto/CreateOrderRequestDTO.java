package com.rockstock.backend.infrastructure.order.dto;

import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderStatus;
import com.rockstock.backend.entity.payment.PaymentMethod;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.entity.warehouse.Warehouse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {

    @NotNull(message = "Delivery cost is required")
    private BigDecimal deliveryCost;

    @NotNull
    private Long addressId;

    @NotNull
    private Long paymentMethodId;

    public Order toEntity(
            Address address,
            PaymentMethod paymentMethod
    ) {
        Order order = new Order();

        order.setDeliveryCost(deliveryCost);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);

        return order;
    }
}
