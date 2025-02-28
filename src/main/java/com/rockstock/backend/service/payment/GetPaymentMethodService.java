package com.rockstock.backend.service.payment;

import com.rockstock.backend.entity.payment.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface GetPaymentMethodService {
    List<PaymentMethod> getAllPaymentMethod();
    Optional<PaymentMethod> getByPaymentMethodName(String methodName);
}
