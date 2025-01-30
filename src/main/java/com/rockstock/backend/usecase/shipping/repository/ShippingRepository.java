package com.rockstock.backend.usecase.shipping.repository;

import com.rockstock.backend.entity.ShippingCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingCost, Long> {
}
