package com.joel.service;

import java.util.Collection;

import com.joel.entity.Payment;
import com.joel.model.PaymentRequestModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface PaymentService {

	Collection<Payment> findAll(int customerId);
	Payment findById(int id);
	void add(int customerId, PaymentRequestModel payment);
	void updateById(int paymentId, PaymentRequestModel payment);
	void deleteById(int paymentId);
}
