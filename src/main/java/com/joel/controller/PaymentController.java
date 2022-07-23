package com.joel.controller;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.message.SuccessMessage;
import com.joel.model.PaymentRequestModel;
import com.joel.model.PaymentResponseModel;
import com.joel.service.PaymentService;

/**
 * 
 * @author joel.rubio
 *
 */
@RestController
@RequestMapping("/api/v1/customers/{customer_id}/payments")
public class PaymentController {

	private PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PaymentResponseModel>> getAll(@PathVariable("customer_id") int customerId) {
		
		Collection<PaymentResponseModel> payments = paymentService.findAll(customerId);
		
		return ResponseEntity.ok(payments);
	}
	
	@GetMapping(path = "/{payment_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentResponseModel> getById(@PathVariable("payment_id") int paymentId) {
		
		PaymentResponseModel payment = paymentService.findByIdAsModel(paymentId);
		
		return ResponseEntity.ok(payment);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> add(@PathVariable("customer_id") int consumerId, @Valid @RequestBody PaymentRequestModel paymentReq) {
		
		paymentService.add(consumerId, paymentReq);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessMessage.builder()
						.status(HttpStatus.CREATED.value())
						.message("Payment created")
						.datetime(LocalDateTime.now())
						.build());
	}
	
	@PatchMapping(path = "/{payment_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateSomeById(@PathVariable("payment_id") int paymentId, @RequestBody PaymentRequestModel paymentReq) {
		
		paymentService.updateById(paymentId, paymentReq);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Payment updated")
				.datetime(LocalDateTime.now())
				.build());
	}
	
	@PutMapping(path = "/{payment_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateAllById(@PathVariable("payment_id") int paymentId, @Valid @RequestBody PaymentRequestModel paymentReq) {
		
		paymentService.updateById(paymentId, paymentReq);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Payment updated")
				.datetime(LocalDateTime.now())
				.build());
	}
	
	@DeleteMapping("/{payment_id}")
	public ResponseEntity<Object> deleteById(@PathVariable("payment_id") int paymentId) {
		
		paymentService.deleteById(paymentId);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Payment deleted")
				.datetime(LocalDateTime.now())
				.build());
	}
}
