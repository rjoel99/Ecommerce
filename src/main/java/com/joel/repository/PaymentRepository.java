package com.joel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	List<Payment> findAllByCustomerId(int customerId);
}
