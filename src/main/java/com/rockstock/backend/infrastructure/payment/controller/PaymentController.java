package com.rockstock.backend.infrastructure.payment.controller;

import com.midtrans.httpclient.error.MidtransError;
import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.service.payment.GetPaymentCategoryService;
import com.rockstock.backend.service.payment.GetPaymentMethodService;
import com.rockstock.backend.service.payment.MidtransPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final MidtransPaymentService midtransPaymentService;
    private final GetPaymentCategoryService getPaymentCategoryService;
    private final GetPaymentMethodService getPaymentMethodService;

    @PostMapping("/get-token")
    public ResponseEntity<?> getTransactionToken(@RequestBody Map<String, Object> requestBody) {
        try {
            Long orderId = Long.parseLong(requestBody.get("order_id").toString());
            Double amount = Double.parseDouble(requestBody.get("amount").toString());

            String transactionToken = midtransPaymentService.createTransactionToken(orderId, amount);
            return ResponseEntity.ok(Map.of("transaction_token", transactionToken));

        } catch (MidtransError | NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody Map<String, Object> payload) {
        midtransPaymentService.processPaymentNotification(payload);
        return ResponseEntity.ok("Notification received successfully");
    }

    // Read / Get
    @GetMapping("/categories")
    public ResponseEntity<?> getAllPaymentCategory() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all payment categories success!", getPaymentCategoryService.getAllPaymentCategory());
    }

    @GetMapping("/categories/name")
    public ResponseEntity<?> getByPaymentCategoryName(@RequestParam String categoryName) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get payment category success!", getPaymentCategoryService.getByPaymentCategoryName(categoryName));
    }

    @GetMapping("/methods")
    public ResponseEntity<?> getAllPaymentMethod() {
        return ApiResponse.success(HttpStatus.OK.value(), "Get all payment categories success!", getPaymentMethodService.getAllPaymentMethod());
    }

    @GetMapping("/methods/categories/name")
    public ResponseEntity<?> getPaymentMethodByPaymentCategoryName(@RequestParam String categoryName) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get payment category success!", getPaymentMethodService.getByPaymentCategoryName(categoryName));
    }

    @GetMapping("/methods/name")
    public ResponseEntity<?> getByPaymentMethodName(@RequestParam String methodName) {
        return ApiResponse.success(HttpStatus.OK.value(), "Get payment category success!", getPaymentMethodService.getByPaymentMethodName(methodName));
    }
}
