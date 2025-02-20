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

    @NotBlank(message = "Payment proof is required")
    private String paymentProof;

    @NotBlank(message = "Delivery cost is required")
    private BigDecimal deliveryCost;

    @NotBlank(message = "Total price is required")
    private BigDecimal totalPrice;

    @NotBlank(message = "Total payment is required")
    private BigDecimal totalPayment;

    @NotNull
    private Long userId;

    @NotNull
    private Long addressId;

    @NotNull
    private Long warehouseId;

    @NotNull
    private Long orderStatusId;

    private Long paymentMethodId;

    public Order toEntity(
            User user,
            Address address,
            Warehouse warehouse,
            OrderStatus orderStatus,
            PaymentMethod paymentMethod
    ) {
        Order order = new Order();

        order.setPaymentProof(paymentProof);
        order.setDeliveryCost(deliveryCost);
        order.setTotalPrice(totalPrice);
        order.setTotalPayment(totalPayment);
        order.setUser(user);
        order.setAddress(address);
        order.setWarehouse(warehouse);
        order.setOrderStatus(orderStatus);
        order.setPaymentMethod(paymentMethod);

        return order;
    }
}
