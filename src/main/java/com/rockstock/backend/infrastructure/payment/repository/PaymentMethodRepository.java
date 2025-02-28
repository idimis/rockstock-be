package com.rockstock.backend.infrastructure.payment.repository;

import com.rockstock.backend.entity.payment.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.name = :methodName")
    Optional<PaymentMethod> findByPaymentMethodName(String methodName);
}
