package com.joel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joel.entity.Cart;
import com.joel.entity.CurrencyCode;
import com.joel.entity.Customer;
import com.joel.entity.Order;
import com.joel.entity.OrderStatus;
import com.joel.entity.Product;
import com.joel.model.OrderRequestModel;
import com.joel.repository.OrderRepository;
import com.joel.service.CartService;

/**
 * 
 * @author joel.rubio
 *
 */
@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private CartService cartService;
	
	@InjectMocks
	@Spy
	private OrderServiceImpl orderService;
	
	
	@DisplayName("Find all orders")
	@Test
	public void findAllWithoutSort() {
		
		//given
		int cartId = 1;
		Customer customer = new Customer("John", "Wick", "3434546554");
		List<Order> orders = List.of(new Order(OrderStatus.CREATED, new Cart(customer)));
		
		//when
		Mockito.when(orderRepository.findAllByCartId(cartId)).thenReturn(orders);
		
		//execute
		orderService.findAll(cartId);
		
		//then
		Mockito.verify(orderRepository, Mockito.times(1)).findAllByCartId(cartId);
	}
	
	@DisplayName("Find order by id")
	@Test
	public void findById() {
		
		//given
		int orderId = 1;
		Customer customer = new Customer("John", "Wick", "3434546554");
		Optional<Order> expectedOrder = Optional.of(new Order(OrderStatus.CREATED, new Cart(customer)));
		Order actualOrder;
		
		//when
		Mockito.when(orderRepository.findById(orderId)).thenReturn(expectedOrder);
		
		//execute
		actualOrder = orderService.findById(orderId);
		
		//then
		assertEquals(expectedOrder.get(), actualOrder);
	}
	
	@DisplayName("Throw an exception if the id doesn't exist")
	@Test
	public void throwExceptionWithInvalidId() {
		
		//given
		int orderId = 0;
		
		//when
		Mockito.when(orderRepository.findById(orderId)).thenThrow(new EntityNotFoundException("The id doesn't exist"));
		
		//then
		assertThrows(EntityNotFoundException.class, () -> orderService.findById(orderId));
	}
	
	@DisplayName("Throw an exception if there are no products to add in a cart")
	@Test
	public void throwExceptionIfThereAreNoProducts() {
		
		//given
		int cartId = 1;
		Customer customer = new Customer("John", "Wick", "3434546554");
		Cart cart = new Cart(customer);
		
		cart.setProducts(List.of());
	
		//when
		Mockito.when(cartService.findById(cartId)).thenReturn(cart);
		
		//then
		assertThrows(IllegalArgumentException.class, () -> orderService.create(cartId));
	}
	
	@DisplayName("Add new order")
	@Test
	public void addNewOrder() {
		
		//given
		int cartId = 1;
		Customer customer = new Customer("John", "Wick", "3434546554");
		Cart cart = new Cart(customer);
		Order order = new Order(OrderStatus.CREATED, cart);
		
		cart.setProducts(List.of(new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN)));

		//when
		Mockito.when(cartService.findById(cartId)).thenReturn(cart);
		Mockito.when(orderRepository.save(order)).thenReturn(order);
		
		//execute
		orderService.create(cartId);
		
		//then
		Mockito.verify(orderRepository, Mockito.times(1)).save(order);
	}
	
	@DisplayName("Update order by id")
	@Test
	public void updateById() {
		
		//given
		int orderId = 1;
		OrderRequestModel orderReq = new OrderRequestModel(OrderStatus.PROCESSING);
		Customer customer = new Customer("John", "Wick", "3434546554");
		Order order = new Order(OrderStatus.CREATED, new Cart(customer));
		
		//when
		Mockito.doReturn(order).when(orderService).findById(orderId);
		Mockito.when(orderRepository.save(order)).thenReturn(order);
		
		//execute
		orderService.updateById(orderId, orderReq);
		
		//then
		Mockito.verify(orderService, Mockito.times(1)).findById(orderId);
		Mockito.verify(orderRepository, Mockito.times(1)).save(order);
	}
}
