package com.joel.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joel.message.SuccessMessage;
import com.joel.model.CartResponseModel;
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
	public ResponseEntity<CartResponseModel> getById(@PathVariable("cart_id") int cartId) {
		
		CartResponseModel cart = cartService.findByIdAsModel(cartId);
		
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping(path = "/{cart_id}")
	public ResponseEntity<Object> addProductToCart(@PathVariable("cart_id") int cartId, @RequestParam("product") int productId) {
		
		cartService.addProductToCart(cartId, productId);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessMessage.builder()
					.status(HttpStatus.CREATED.value())
					.message("Product added to cart")
					.datetime(LocalDateTime.now())
					.build());
	}
	
	@DeleteMapping(path = "/{cart_id}")
	public ResponseEntity<Object> removeProductFromCart(@PathVariable("cart_id") int cartId, @RequestParam("product") int productId) {
		
		cartService.removeProductFromCart(cartId, productId);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Product removed from cart")
				.datetime(LocalDateTime.now())
				.build());
	}
}
