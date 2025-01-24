package com.rockstock.backend.infrastructure.usecase.shipping.service;

import com.rockstock.backend.entity.ShippingCost;
import com.rockstock.backend.infrastructure.usecase.shipping.dto.ShippingCostRequestDTO;
import com.rockstock.backend.infrastructure.usecase.shipping.dto.ShippingCostResponseDTO;
import com.rockstock.backend.infrastructure.usecase.shipping.dto.ShippingMethodResponseDTO;
import com.rockstock.backend.infrastructure.usecase.shipping.repository.ShippingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public ShippingService(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    public ShippingCostResponseDTO calculateShippingCost(ShippingCostRequestDTO requestDTO) {
        // Mock distance calculation (in a real scenario, use Google Maps API or other geolocation services)
        double distance = calculateDistance(requestDTO.getOrigin(), requestDTO.getDestination());

        // Mock available shipping methods
        List<ShippingMethodResponseDTO> methods = getAvailableShippingMethods();

        // Calculate costs for each method
        List<ShippingCostResponseDTO> shippingCosts = new ArrayList<>();
        for (ShippingMethodResponseDTO method : methods) {
            double cost = distance * method.getCostPerKm();
            ShippingCostResponseDTO responseDTO = new ShippingCostResponseDTO();
            responseDTO.setOrigin(requestDTO.getOrigin());
            responseDTO.setDestination(requestDTO.getDestination());
            responseDTO.setDistance(distance);
            responseDTO.setCost(cost);
            responseDTO.setShippingMethod(method.getMethodName());
            shippingCosts.add(responseDTO);
        }

        // Save the first shipping method cost for example purposes
        ShippingCost shippingCost = new ShippingCost();
        shippingCost.setOrigin(requestDTO.getOrigin());
        shippingCost.setDestination(requestDTO.getDestination());
        shippingCost.setDistance(distance);
        shippingCost.setCost(shippingCosts.get(0).getCost());
        shippingCost.setShippingMethod(shippingCosts.get(0).getShippingMethod());
        shippingRepository.save(shippingCost);

        return shippingCosts.get(0); // Return the first shipping method as the selected one
    }

    private double calculateDistance(String origin, String destination) {
        // Mock logic for distance calculation (replace with API for real-world use)
        return 10.0; // Assume 10 km for testing
    }

    private List<ShippingMethodResponseDTO> getAvailableShippingMethods() {
        List<ShippingMethodResponseDTO> methods = new ArrayList<>();

        ShippingMethodResponseDTO method1 = new ShippingMethodResponseDTO();
        method1.setMethodName("Standard Shipping");
        method1.setCostPerKm(1.0);

        ShippingMethodResponseDTO method2 = new ShippingMethodResponseDTO();
        method2.setMethodName("Express Shipping");
        method2.setCostPerKm(2.5);

        methods.add(method1);
        methods.add(method2);

        return methods;
    }
}
