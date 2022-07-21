package com.joel.service.impl;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.joel.entity.Cart;
import com.joel.entity.Customer;
import com.joel.entity.Product;
import com.joel.repository.CartRepository;
import com.joel.service.CartService;
import com.joel.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author joel.rubio
 *
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

	private CartRepository cartRepository;
	private ProductService productService;
	
	public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
		this.cartRepository = cartRepository;
		this.productService = productService;
	}

	@Override
	public Cart findById(int cartId) {
		
		log.info("Getting cart by id {}...", cartId);
		
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new EntityNotFoundException("The cart with id " + cartId + " doesn't exist"));
		
		log.info("Cart by id {} obtained", cartId);
		
		return cart;
	}

	@Override
	public void create(Customer customer) {
		
		log.info("Creating cart for customer with id {}...", customer.getId());
		
		Cart cart = new Cart(BigDecimal.ZERO, customer);
		
		cartRepository.save(cart);
		
		log.info("Cart for customer id {} created", customer.getId());
	}

	@Override
	public void updateById(int cartId, int productId) {
		
		Cart cart = findById(cartId);
		
		Product product = productService.findById(productId);
		
		log.info("Updating cart with id {}...", cartId);
		
		cart.addProduct(product);
		
		cartRepository.save(cart);
		
		log.info("Cart with id {} updated", cartId);
	}
}
