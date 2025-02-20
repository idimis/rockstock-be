package com.rockstock.backend.service.order.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.common.exceptions.UnauthorizedException;
import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderItem;
import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.order.dto.CreateOrderItemRequestDTO;
import com.rockstock.backend.infrastructure.order.repository.OrderItemRepository;
import com.rockstock.backend.infrastructure.order.repository.OrderRepository;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.service.order.CreateOrderItemService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateOrderItemServiceImpl implements CreateOrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderItem createOrderItem(CreateOrderItemRequestDTO req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        Long userId = Claims.getUserIdFromJwt();

        if (!order.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Unauthorized: Order does not belong to the user");
        }

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        Optional<OrderItem> checkOrderItem = orderItemRepository.findByOrderIdAndProductId(req.getOrderId(), req.getProductId());
        if (checkOrderItem.isPresent()) {
            throw new DuplicateRequestException("Order item is already exist !");
        }

        OrderItem newOrderItem = new OrderItem();

        newOrderItem.setQuantity(req.getQuantity());
        newOrderItem.setPrice(product.getPrice());
        newOrderItem.setOrder(order);
        newOrderItem.setProduct(product);

        return orderItemRepository.save(newOrderItem);
    }
}
