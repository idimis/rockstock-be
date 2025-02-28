package com.rockstock.backend.service.payment.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.payment.PaymentMethod;
import com.rockstock.backend.infrastructure.payment.repository.PaymentMethodRepository;
import com.rockstock.backend.service.payment.GetPaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetPaymentMethodServiceImpl implements GetPaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    @Transactional
    public List<PaymentMethod> getAllPaymentMethod() {
        return paymentMethodRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<PaymentMethod> getByPaymentMethodName(String methodName) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findByPaymentMethodName(methodName);
        if (paymentMethod.isEmpty()){
            throw new DataNotFoundException("Payment method not found !");
        }
        return paymentMethod;
    }
}
