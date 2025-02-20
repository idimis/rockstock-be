package com.rockstock.backend.service.order.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.UnauthorizedException;
import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderItem;
import com.rockstock.backend.infrastructure.order.repository.OrderItemRepository;
import com.rockstock.backend.infrastructure.order.repository.OrderRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.order.GetOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetOrderItemServiceImpl implements GetOrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public List<OrderItem> getAllByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        Long userId = Claims.getUserIdFromJwt();

        if (!order.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Unauthorized: Order does not belong to the user");
        }

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        if (orderItems.isEmpty()){
            throw new DataNotFoundException("Order item not found !");
        }
        return orderItems;
    }

    @Override
    @Transactional
    public Optional<OrderItem> getByOrderIdAndProductId(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        Long userId = Claims.getUserIdFromJwt();

        if (!order.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Unauthorized: Order does not belong to the user");
        }

        Optional<OrderItem> orderItem = orderItemRepository.findByOrderIdAndProductId(orderId, productId);
        if (orderItem.isEmpty()){
            throw new DataNotFoundException("Order item not found !");
        }
        return orderItem;
    }

    @Override
    public Optional<OrderItem> getByOrderIdAndId(Long orderId, Long id) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        Long userId = Claims.getUserIdFromJwt();

        if (!order.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Unauthorized: Order does not belong to the user");
        }

        Optional<OrderItem> orderItemById = orderItemRepository.findByOrderIdAndId(orderId, id);
        if (orderItemById.isEmpty()){
            throw new DataNotFoundException("Order item not found !");
        }
        return orderItemById;
    }
}
