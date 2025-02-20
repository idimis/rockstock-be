package com.rockstock.backend.service.payment;

import com.rockstock.backend.entity.payment.PaymentCategory;

import java.util.List;
import java.util.Optional;

public interface GetPaymentCategoryService {
    List<PaymentCategory> getAllPaymentCategory();
    Optional<PaymentCategory> getByPaymentCategoryName(String categoryName);
}
