package com.rockstock.backend.infrastructure.payment.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.service.payment.GetPaymentCategoryService;
import com.rockstock.backend.service.payment.GetPaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final GetPaymentCategoryService getPaymentCategoryService;
    private final GetPaymentMethodService getPaymentMethodService;

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
