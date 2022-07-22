package com.joel.service.impl;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.joel.entity.Cart;
import com.joel.entity.Order;
import com.joel.entity.OrderStatus;
import com.joel.model.OrderRequestModel;
import com.joel.repository.OrderRepository;
import com.joel.service.CartService;
import com.joel.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author joel.rubio
 *
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private CartService cartService;
	
	public OrderServiceImpl(OrderRepository orderRepository, CartService cartService) {
		this.orderRepository = orderRepository;
		this.cartService = cartService;
	}

	@Override
	public Collection<Order> findAllByCartId(int cartId) {

		log.info("Getting all orders by cart id {}...", cartId);
		
		Collection<Order> orders = orderRepository.findAllByCartId(cartId);
		
		log.info("Orders by cart id {} obtained", cartId);
		
		return orders;
	}

	@Override
	public Order findById(int orderId) {
		
		log.info("Getting order with id {}...", orderId);
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("The order with id " + orderId + " doesn't exist"));
		
		log.info("Order with id {} obtained", orderId);
		
		return order;
	}

	@Override
	public Order create(int cartId) {
		
		Cart cart = cartService.findById(cartId);
		
		if (cart.areThereAnyProducts())
			throw new IllegalArgumentException("There are no products in the cart of the customer");
		
		log.info("Creating new order by cart id {}...", cartId);

		Order order = new Order(OrderStatus.CREATED, cart);
		
		orderRepository.save(order);
		
		log.info("Order created");
		
		return order;
	}

	@Override
	public void updateById(int orderId, OrderRequestModel orderReq) {
		
		Order order = findById(orderId);
		
		log.info("Updating order with id {}...", orderId);
		
		order.setStatus(orderReq.getStatus());
		
		orderRepository.save(order);
		
		log.info("Order with id {} updated", orderId);
	}
}
