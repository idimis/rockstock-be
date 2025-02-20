package com.rockstock.backend.service.order.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.order.OrderStatus;
import com.rockstock.backend.infrastructure.order.repository.OrderStatusRepository;
import com.rockstock.backend.service.order.GetOrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetOrderStatusServiceImpl implements GetOrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Override
    @Transactional
    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<OrderStatus> getByOrderStatusName(String statusName) {
        Optional<OrderStatus> orderStatus = orderStatusRepository.findByStatusName(statusName);
        if (orderStatus.isEmpty()) {
            throw new DataNotFoundException("Order status not found !");
        }
        return orderStatus;
    }
}
