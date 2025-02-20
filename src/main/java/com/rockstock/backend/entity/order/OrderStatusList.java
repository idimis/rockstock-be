package com.rockstock.backend.entity.order;

public enum OrderStatusList {
    WAITING_FOR_PAYMENT,
    WAITING_FOR_PAYMENT_CONFIRMATION,
    PROCESSING,
    ON_DELIVERY,
    COMPLETED,
    CANCELED
}
