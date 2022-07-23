package com.joel.service.impl;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.joel.entity.Customer;
import com.joel.entity.Payment;
import com.joel.model.PaymentRequestModel;
import com.joel.model.PaymentResponseModel;
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
	public Collection<PaymentResponseModel> findAll(int customerId) {
		
		log.info("Getting all payments by customer id {}...", customerId);
		
		Collection<Payment> payments = paymentRepository.findAllByCustomerId(customerId);
		
		log.info("Payments by customer id {} obtained", customerId);
		
		return PaymentResponseModel.fromEntitiesToModels(payments);
	}
	
	@Override
	public Payment findById(int paymentId) {
		
		log.info("Getting payment by id {}...", paymentId);
		
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new EntityNotFoundException("The payment with id " + paymentId + " doesn't exist"));
		
		log.info("Payment with id {} obtained", paymentId);
		
		return payment;
	}
	
	@Override
	public PaymentResponseModel findByIdAsModel(int paymentId) {
		
		Payment payment = findById(paymentId);
		
		return PaymentResponseModel.fromEntityToModel(payment);
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
		
		return PaymentRequestModel.createPayment(paymentReq, customer);
	}
	
	private void updateEntityFromRequest(Payment payment, PaymentRequestModel paymentReq) {
		
		PaymentRequestModel.updatePayment(payment, paymentReq);
	}

	
	@Override
	public void updateById(int paymentId, PaymentRequestModel paymentReq) {
		
		Payment payment = findById(paymentId);
		
		log.info("Updating payment with id {}...", paymentId);

		updateEntityFromRequest(payment, paymentReq);
		
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
