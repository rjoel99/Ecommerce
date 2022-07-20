package com.joel.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.joel.entity.Customer;
import com.joel.entity.Payment;
import com.joel.model.PaymentRequestModel;
import com.joel.repository.PaymentRepository;
import com.joel.service.CustomerService;
import com.joel.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author joel.rubio
 *
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;
	private CustomerService customerService;
	
	public PaymentServiceImpl(PaymentRepository paymentRepository, CustomerService customerService) {
		this.paymentRepository = paymentRepository;
		this.customerService = customerService;
	}

	@Override
	public Collection<Payment> findAll(int customerId) {
		
		log.info("Getting all payments by customer id {}...", customerId);
		
		Collection<Payment> payments = paymentRepository.findAllByCustomerId(customerId);
		
		log.info("Payments by customer id {} obtained", customerId);
		
		return payments;
	}

	@Override
	public Payment findById(int id) {
		
		log.info("Getting payment by id {}...", id);
		
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("The payment with id " + id + " doesn't exist"));
		
		log.info("Payment with id {} obtained", id);
		
		return payment;
	}

	@Override
	public void add(int customerId, PaymentRequestModel paymentReq) {
		
		Customer customer = customerService.findById(customerId);
		
		log.info("Adding new payment for customer with id {}...", customerId);

		Payment payment = createFromRequestToEntity(paymentReq, customer);
		
		paymentRepository.save(payment);
		
		log.info("Payment added for customer with id {}", customerId);
	}
	

	private Payment createFromRequestToEntity(PaymentRequestModel paymentReq, Customer customer) {
		
		Payment payment = null;
		
		for (Map.Entry<Class<? extends PaymentRequestModel>, BiFunction<PaymentRequestModel, Customer, Payment>> paymentType : 
			 PaymentRequestModel.getPaymentsToCreate().entrySet()) {
			
			if (paymentReq.getClass().isAssignableFrom(paymentType.getKey()))
				payment = paymentType.getValue().apply(paymentReq, customer);
		}
		
		if (payment == null)
			throw new IllegalArgumentException("There is no supported payment");
		
		return payment;
	}
	
	private void updateEntity(Payment payment, PaymentRequestModel paymentReq) {
		
		for (Map.Entry<Class<? extends PaymentRequestModel>, BiConsumer<Payment, PaymentRequestModel>> paymentType : 
			 PaymentRequestModel.getPaymentsToUpdate().entrySet()) {
			
			if (paymentReq.getClass().isAssignableFrom(paymentType.getKey()))
				paymentType.getValue().accept(payment, paymentReq);
		}
	}

	
	@Override
	public void updateById(int paymentId, PaymentRequestModel paymentReq) {
		
		Payment payment = findById(paymentId);
		
		log.info("Updating payment with id {}...", paymentId);

		updateEntity(payment, paymentReq);
		
		paymentRepository.save(payment);
		
		log.info("Payment with id {} updated", paymentId);
	}

	@Override
	public void deleteById(int paymentId) {
		
		Payment payment = findById(paymentId);
		
		log.info("Deleting payment with id {}...", paymentId);
		
		paymentRepository.delete(payment);
		
		log.info("Payment with id {} deleted", paymentId);
	}
}
