package com.rockstock.backend.service.payment.impl;

import com.midtrans.Config;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderStatusList;
import com.rockstock.backend.infrastructure.order.repository.OrderRepository;
import com.rockstock.backend.service.order.UpdateOrderService;
import com.rockstock.backend.service.payment.MidtransPaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MidtransPaymentServiceImpl implements MidtransPaymentService {

    private final Config midtransConfig;
    private final OrderRepository orderRepository;
    private final UpdateOrderService updateOrderService;

    @Override
    @Transactional
    public String createTransactionToken(Long orderId, Double amount) throws MidtransError {
        // Generate a unique order ID if needed
        String uniqueOrderId = orderId != null ? "ORDER-" + orderId : UUID.randomUUID().toString();

        // Transaction details
        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", uniqueOrderId);
        transactionDetails.put("gross_amount", amount);

        // Credit card settings
        Map<String, Object> creditCard = new HashMap<>();
        creditCard.put("secure", true);

        // Main request body
        Map<String, Object> params = new HashMap<>();
        params.put("transaction_details", transactionDetails);
        params.put("credit_card", creditCard);

        // Convert to JSON
        JSONObject jsonParams = new JSONObject(params);

        // Generate transaction token
        return SnapApi.createTransactionToken(params, midtransConfig);
    }

    @Override
    @Transactional
    public void processPaymentNotification(Map<String, Object> payload) {
        String orderId = (String) payload.get("order_id");
        String transactionStatus = (String) payload.get("transaction_status");

        Optional<Order> orderOptional = orderRepository.findByOrderCode(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }

        Order order = orderOptional.get();

        if ("settlement".equals(transactionStatus)) {
            // Change status to PROCESSING
            updateOrderService.updateOrderStatus(OrderStatusList.PROCESSING, order.getId(), null);
        } else if ("deny".equals(transactionStatus) || "cancel".equals(transactionStatus) || "expire".equals(transactionStatus)) {
            // Payment failed or expired → Change to CANCELED
            updateOrderService.updateOrderStatus(OrderStatusList.CANCELED, order.getId(), null);
        }

        orderRepository.save(order);
    }
}
