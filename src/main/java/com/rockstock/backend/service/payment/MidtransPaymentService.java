package com.rockstock.backend.service.payment;

import com.midtrans.httpclient.error.MidtransError;

import java.util.Map;

public interface MidtransPaymentService {
    String createTransactionToken(Long orderId, Double amount) throws MidtransError;
    void processPaymentNotification(Map<String, Object> payload);
}
