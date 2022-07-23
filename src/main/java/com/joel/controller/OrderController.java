package com.joel.controller;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.message.SuccessMessage;
import com.joel.model.OrderRequestModel;
import com.joel.model.OrderResponseModel;
import com.joel.service.OrderService;

/**
 * @author joel.rubio
 *
 */
@RestController
@RequestMapping("/api/v1/customers/{customer_id}/carts/{cart_id}/orders")
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<OrderResponseModel>> getAll(@PathVariable("cart_id") int cartId) {
		
		Collection<OrderResponseModel> orders = orderService.findAll(cartId);
		
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping(path = "/{order_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResponseModel> getById(@PathVariable("order_id") int orderId) {
		
		OrderResponseModel order = orderService.findByIdAsModel(orderId);
		
		return ResponseEntity.ok(order);
	}
	
	@PostMapping
	public ResponseEntity<OrderResponseModel> create(@PathVariable("cart_id") int cartId) {
		
		OrderResponseModel order = orderService.create(cartId);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(order);
	}
	
	@PutMapping(path = "/{order_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateById(@PathVariable("order_id") int orderId, @Valid @RequestBody OrderRequestModel orderReq) {
		
		orderService.updateById(orderId, orderReq);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Order updated")
				.datetime(LocalDateTime.now())
				.build());
	}
}
