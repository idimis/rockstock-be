package com.rockstock.backend.usecase.address.repository;

import com.rockstock.backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
    Address findByUserIdAndIsPrimaryTrue(Long userId);
}
