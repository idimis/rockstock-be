package com.rockstock.backend.infrastructure.payment.repository;

import com.rockstock.backend.entity.payment.PaymentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, Long> {

    @Query("SELECT pc FROM PaymentCategory pc WHERE pc.name = :categoryName")
    Optional<PaymentCategory> findByPaymentCategoryName(String categoryName);
}
