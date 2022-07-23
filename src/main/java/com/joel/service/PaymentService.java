package com.joel.service;

import java.util.Collection;

import com.joel.entity.Payment;
import com.joel.model.PaymentRequestModel;
import com.joel.model.PaymentResponseModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface PaymentService {

	Collection<PaymentResponseModel> findAll(int customerId);
	PaymentResponseModel findByIdAsModel(int paymentId);
	Payment findById(int paymentId);
	void add(int customerId, PaymentRequestModel payment);
	void updateById(int paymentId, PaymentRequestModel payment);
	void deleteById(int paymentId);
}
