package com.joel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.joel.entity.Customer;

/**
 * @author joel.rubio
 *
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	@DisplayName("Injected component repository is not null")
	@Test
	public void componentRepositoryNotNull() {
		
		assertThat(customerRepository).isNotNull();
	}
	
	@DisplayName("Find all customers from database")
	@Test
	public void findAllFromDatabase() {
		
		//given
		Customer customer1 = new Customer("John", "Wick", "4354654323");
		Customer customer2 = new Customer("Margot", "Robbie", "6778895645");
		
		//execute
		customerRepository.saveAll(List.of(customer1, customer2));
		
		//then
		assertThat(customerRepository.findAll().isEmpty()).isFalse();
	}
	
	@DisplayName("Find customer by id from database")
	@Test
	public void findByIdFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		
		//execute
		customerRepository.save(customer);
		
		//then
		assertThat(customerRepository.findById(customer.getId()).isPresent()).isTrue();
	}
	
	@DisplayName("Verify that a customer with false id doesn't exist")
	@Test
	public void verifyProductNotExist() {
		
		//assert
		assertThat(customerRepository.findById(1).isEmpty()).isTrue();
	}
	
	@DisplayName("Add new customer to the database")
	@Test
	public void addNewCustomerToDatabase() {
		
		//given
		Customer expectedCustomer = new Customer("John", "Wick", "4354654323");
		Customer actualCustomer;
		
		//execute
		actualCustomer = customerRepository.save(expectedCustomer);
		
		//then
		assertThat(actualCustomer).isEqualTo(expectedCustomer);
	}
	
	@DisplayName("Delete customer from database")
	@Test
	public void deleteFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		
		//execute
		customer = customerRepository.save(customer);
		
		customerRepository.delete(customer);
		
		//then
		assertThat(customerRepository.findById(customer.getId()).isEmpty()).isTrue();
	}
}
