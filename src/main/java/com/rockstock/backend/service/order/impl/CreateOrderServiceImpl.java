package com.rockstock.backend.service.order.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.entity.order.Order;
import com.rockstock.backend.entity.order.OrderStatus;
import com.rockstock.backend.entity.payment.PaymentMethod;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.entity.warehouse.Warehouse;
import com.rockstock.backend.infrastructure.address.repository.AddressRepository;
import com.rockstock.backend.infrastructure.order.dto.CreateOrderRequestDTO;
import com.rockstock.backend.infrastructure.order.repository.OrderRepository;
import com.rockstock.backend.infrastructure.order.repository.OrderStatusRepository;
import com.rockstock.backend.infrastructure.payment.repository.PaymentMethodRepository;
import com.rockstock.backend.infrastructure.user.auth.security.Claims;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.infrastructure.warehouse.repository.WarehouseRepository;
import com.rockstock.backend.service.order.CreateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderServiceImpl implements CreateOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final WarehouseRepository warehouseRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Override
    @Transactional
    public Order createOrder(CreateOrderRequestDTO req) {

        Long userId = Claims.getUserIdFromJwt();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Address address = addressRepository.findById(req.getAddressId())
                .orElseThrow(() -> new DataNotFoundException("Address not found"));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(req.getPaymentMethodId())
                .orElseThrow(() -> new DataNotFoundException("Payment Method not found"));

        OrderStatus orderStatus = orderStatusRepository.findById(req.getOrderStatusId())
                .orElseThrow(() -> new DataNotFoundException("Order status not found"));

        // Find the nearest warehouse
        Warehouse nearestWarehouse = findNearestWarehouse(address.getLatitude(), address.getLongitude());

        // Create the order entity
        Order order = new Order();

        order.setPaymentProof(req.getPaymentProof());
        order.setDeliveryCost(req.getDeliveryCost());
        order.setTotalPrice(req.getTotalPrice());
        order.setTotalPayment(req.getTotalPayment());
        order.setUser(user);
        order.setAddress(address);
        order.setWarehouse(nearestWarehouse);
        order.setOrderStatus(orderStatus);
        order.setPaymentMethod(paymentMethod);

        return orderRepository.save(order);
    }

    // Functions
    private Warehouse findNearestWarehouse(String addressLat, String addressLng) {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        Warehouse nearestWarehouse = null;
        double minDistance = Double.MAX_VALUE;

        double lat1 = Double.parseDouble(addressLat);
        double lng1 = Double.parseDouble(addressLng);

        for (Warehouse warehouse : warehouses) {
            double lat2 = Double.parseDouble(warehouse.getLatitude());
            double lng2 = Double.parseDouble(warehouse.getLongitude());

            double distance = haversine(lat1, lng1, lat2, lng2);
            if (distance < minDistance) {
                minDistance = distance;
                nearestWarehouse = warehouse;
            }
        }

        if (nearestWarehouse == null) {
            throw new RuntimeException("No warehouse found");
        }
        return nearestWarehouse;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
