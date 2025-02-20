package com.rockstock.backend.service.payment.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.payment.PaymentCategory;
import com.rockstock.backend.infrastructure.payment.repository.PaymentCategoryRepository;
import com.rockstock.backend.service.payment.GetPaymentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetPaymentCategoryServiceImpl implements GetPaymentCategoryService {

    private final PaymentCategoryRepository paymentCategoryRepository;

    @Override
    @Transactional
    public List<PaymentCategory> getAllPaymentCategory() {
        return paymentCategoryRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<PaymentCategory> getByPaymentCategoryName(String categoryName) {
        Optional<PaymentCategory> paymentCategory = paymentCategoryRepository.findByPaymentCategoryName(categoryName);
        if (paymentCategory.isEmpty()){
            throw new DataNotFoundException("Payment category not found !");
        }
        return paymentCategory;
    }
}
