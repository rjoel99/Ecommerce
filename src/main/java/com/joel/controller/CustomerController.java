package com.joel.controller;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joel.message.SuccessMessage;
import com.joel.model.CustomerRequestModel;
import com.joel.model.CustomerResponseModel;
import com.joel.service.CustomerService;

/**
 * @author joel.rubio
 *
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<CustomerResponseModel>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page, 
													   @RequestParam(defaultValue = "5", required = false) Integer size,
													   @RequestParam(required = false) String[] sort) {
		
		Collection<CustomerResponseModel> customers = customerService.findAll(page, size, sort);
		
		return ResponseEntity.ok(customers);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponseModel> getById(@PathVariable int id) {
		
		CustomerResponseModel customer = customerService.findByIdAsModel(id);
		
		return ResponseEntity.ok(customer);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> add(@Valid @RequestBody CustomerRequestModel customerRequestModel) {
		
		customerService.add(customerRequestModel);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessMessage.builder()
						.status(HttpStatus.OK.value())
						.message("Customer added")
						.datetime(LocalDateTime.now())
						.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable int id) {
		
		customerService.deleteById(id);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Customer deleted")
				.datetime(LocalDateTime.now())
				.build());
	}
}
