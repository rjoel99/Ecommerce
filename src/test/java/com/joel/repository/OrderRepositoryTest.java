package com.joel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.joel.entity.Cart;
import com.joel.entity.Customer;
import com.joel.entity.Order;
import com.joel.entity.OrderStatus;

/**
 * @author joel.rubio
 *
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@DisplayName("Injected component repository is not null")
	@Test
	public void componentRepositoryNotNull() {
		
		assertThat(orderRepository).isNotNull();
		assertThat(cartRepository).isNotNull();
		assertThat(customerRepository).isNotNull();
	}
	
	@DisplayName("Find all customers from database")
	@Test
	public void findAllFromDatabase() {
		
		//given
		Customer customer1 = new Customer("John", "Wick", "4354654323");
		Customer customer2 = new Customer("Margot", "Robbie", "6778895645");
		Cart cart1 = new Cart();
		Cart cart2 = new Cart();
		Order order1 = new Order();
		Order order2 = new Order();
		
		//execute
		customerRepository.saveAll(List.of(customer1, customer2));
		
		cart1.setCustomer(customer1);
		cart2.setCustomer(customer2);
		
		cartRepository.saveAll(List.of(cart1, cart2));
		
		order1.setStatus(OrderStatus.CREATED);
		order1.setCart(cart1);
		
		order2.setStatus(OrderStatus.CREATED);
		order2.setCart(cart2);
		
		orderRepository.saveAll(List.of(order1, order2));
		
		//then
		assertThat(orderRepository.findAll().isEmpty()).isFalse();
	}
	
	@DisplayName("Find customer by id from database")
	@Test
	public void findByIdFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Cart cart = new Cart();
		Order order = new Order();
		
		//execute
		customerRepository.save(customer);
		
		cart.setCustomer(customer);
		
		cartRepository.save(cart);
		
		order.setStatus(OrderStatus.CREATED);
		order.setCart(cart);
		
		orderRepository.save(order);
		
		//then
		assertThat(orderRepository.findById(customer.getId()).isPresent()).isTrue();
	}
	
	@DisplayName("Verify that an order with false id doesn't exist")
	@Test
	public void verifyProductNotExist() {
		
		//assert
		assertThat(orderRepository.findById(1).isEmpty()).isTrue();
	}
	
	@DisplayName("Add new order to the database")
	@Test
	public void addNewCustomerToDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Cart cart = new Cart();
		Order expectedOrder = new Order();
		Order actualOrder;
		
		//execute
		customerRepository.save(customer);
		
		cart.setCustomer(customer);
		
		cartRepository.save(cart);
		
		expectedOrder.setStatus(OrderStatus.CREATED);
		expectedOrder.setCart(cart);
		
		actualOrder = orderRepository.save(expectedOrder);
		
		//then
		assertThat(actualOrder).isEqualTo(expectedOrder);
	}
	
	@DisplayName("Delete order from database")
	@Test
	public void deleteFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Cart cart = new Cart();
		Order order = new Order();
		
		//execute
		customerRepository.save(customer);
		
		cart.setCustomer(customer);
		
		cartRepository.save(cart);
		
		order.setStatus(OrderStatus.CREATED);
		order.setCart(cart);
		
		order = orderRepository.save(order);
		orderRepository.delete(order);
		
		//then
		assertThat(orderRepository.findById(order.getId()).isEmpty()).isTrue();
	}
}
