package com.rockstock.backend.service.order;

public interface OrderSchedulerService {
    void cancelExpiredOrders();
    void autoCompleteOrders();
}
