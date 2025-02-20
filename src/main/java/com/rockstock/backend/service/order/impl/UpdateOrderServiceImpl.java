package com.rockstock.backend.service.order.impl;

import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderStatusList;
import com.rockstock.backend.infrastructure.order.dto.UpdateOrderRequestDTO;
import com.rockstock.backend.service.order.UpdateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderServiceImpl implements UpdateOrderService {

    @Override
    public Order updateOrderStatus(OrderStatusList status, Long warehouseId, Long paymentMethodId, UpdateOrderRequestDTO req) {

//        switch (status){
//            case WAITING_FOR_PAYMENT ->
//        }
        String statusStr = status.toString();



        return null;
    }



}
