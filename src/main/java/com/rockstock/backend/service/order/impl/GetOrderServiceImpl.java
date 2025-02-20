package com.rockstock.backend.service.order.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.infrastructure.order.repository.OrderRepository;
import com.rockstock.backend.service.order.GetOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetOrderServiceImpl implements GetOrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public List<Order> getAllByUserId(Long userId) {
        List<Order> userOrders = orderRepository.findAllByUserId(userId);
        if (userOrders.isEmpty()){
            throw new DataNotFoundException("Order not found !");
        }
        return userOrders;
    }

    @Override
    @Transactional
    public List<Order> getAllByWarehouseId(Long warehouseId) {
        List<Order> warehouseOrders = orderRepository.findAllByWarehouseId(warehouseId);
        if (warehouseOrders.isEmpty()){
            throw new DataNotFoundException("Order not found !");
        }
        return warehouseOrders;
    }

    @Override
    @Transactional
    public List<Order> getAllByUserIdAndOrderStatusName(Long userId, String statusName) {
        List<Order> userOrdersByStatus = orderRepository.findAllByUserIdAndOrderStatusName(userId, statusName);
        if (userOrdersByStatus.isEmpty()){
            throw new DataNotFoundException("Order not found !");
        }
        return userOrdersByStatus;
    }

    @Override
    @Transactional
    public List<Order> getAllByWarehouseIdAndOrderStatusName(Long warehouseId, String statusName) {
        List<Order> warehouseOrdersByStatus = orderRepository.findAllByWarehouseIdAndOrderStatusName(warehouseId, statusName);
        if (warehouseOrdersByStatus.isEmpty()){
            throw new DataNotFoundException("Order not found !");
        }
        return warehouseOrdersByStatus;
    }

    @Override
    @Transactional
    public List<Order> getAllByPaymentMethodName(String methodName) {
        List<Order> ordersByPaymentMethod = orderRepository.findAllByPaymentMethodName(methodName);
        if (ordersByPaymentMethod.isEmpty()){
            throw new DataNotFoundException("Order not found !");
        }
        return ordersByPaymentMethod;
    }

    @Override
    @Transactional
    public Optional<Order> getById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()){
            throw new DataNotFoundException("Order not found !");
        }
        return order;
    }
}
