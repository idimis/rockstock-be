package com.rockstock.backend.entity.order;

public enum OrderStatusList {
    WAITING_FOR_PAYMENT,
    PAYMENT_VERIFICATION,
    PROCESSING,
    ON_DELIVERY,
    DELIVERED,
    COMPLETED,
    CANCELED
}
