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

/**
 * @author joel.rubio
 *
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartRepositoryTest {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@DisplayName("Injected component repository is not null")
	@Test
	public void componentRepositoryNotNull() {
		
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
		
		//execute
		customerRepository.save(customer1);
		customerRepository.save(customer2);
		
		cart1.setCustomer(customer1);
		cart2.setCustomer(customer2);
		
		cartRepository.saveAll(List.of(cart1, cart2));
		
		//then
		assertThat(cartRepository.findAll().isEmpty()).isFalse();
	}
	
	@DisplayName("Find customer by id from database")
	@Test
	public void findByIdFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Cart cart = new Cart();
		
		//execute
		customerRepository.save(customer);
		
		cart.setCustomer(customer);
		
		cartRepository.save(cart);
		
		//then
		assertThat(cartRepository.findById(cart.getId()).isPresent()).isTrue();
	}
	
	@DisplayName("Verify that a customer with false id doesn't exist")
	@Test
	public void verifyProductNotExist() {
		
		//assert
		assertThat(cartRepository.findById(1).isEmpty()).isTrue();
	}
	
	@DisplayName("Add new customer to the database")
	@Test
	public void addNewCustomerToDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Cart expectedCart = new Cart();
		Cart actualCart;
		
		//execute
		customerRepository.save(customer);
		
		expectedCart.setCustomer(customer);
		
		actualCart = cartRepository.save(expectedCart);
		
		//then
		assertThat(actualCart).isEqualTo(expectedCart);
	}
	
	@DisplayName("Delete customer from database")
	@Test
	public void deleteFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Cart cart = new Cart();
		
		//execute
		customerRepository.save(customer);
		
		cart.setCustomer(customer);
		
		cartRepository.save(cart);
		cartRepository.delete(cart);
		
		//then
		assertThat(cartRepository.findById(cart.getId()).isEmpty()).isTrue();
	}
}
