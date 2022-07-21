package com.joel.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.entity.Cart;
import com.joel.message.SuccessMessage;
import com.joel.service.CartService;

/**
 * 
 * @author joel.rubio
 *
 */
@RestController
@RequestMapping("/api/v1/customers/{customer_id}/carts")
public class CartController {

	private CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping(path = "/{cart_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cart> getByid(@PathVariable("cart_id") int cartId) {
		
		Cart cart = cartService.findById(cartId);
		
		return ResponseEntity.ok(cart);
	}
	
	@PutMapping(path = "/{cart_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateById(@PathVariable("cart_id") int cartId) {
		
		cartService.updateById(cartId, 0);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Cart updated")
				.datetime(LocalDateTime.now())
				.build());
	}
}
