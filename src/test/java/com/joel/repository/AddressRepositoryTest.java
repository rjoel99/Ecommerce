package com.joel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.joel.entity.Address;
import com.joel.entity.Customer;

/**
 * @author joel.rubio
 *
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AddressRepositoryTest {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@DisplayName("Injected component repository is not null")
	@Test
	public void componentRepositoryNotNull() {
		
		assertThat(addressRepository).isNotNull();
		assertThat(customerRepository).isNotNull();
	}
	
	@DisplayName("Find all addresses from database")
	@Test
	public void findAllFromDatabase() {
		
		//given
		Customer customer1 = new Customer("John", "Wick", "4354654323");
		Customer customer2 = new Customer("Margot", "Robbie", "6778895645");
		Address address1 = new Address("Some street1", "Some city1", "Some state1", "12345");
		Address address2 = new Address("Some street2", "Some city2", "Some state2", "67890");
		
		//execute
		customerRepository.saveAll(List.of(customer1, customer2));
		
		address1.setCustomer(customer1);
		address2.setCustomer(customer2);
		
		addressRepository.saveAll(List.of(address1, address2));
		
		//then
		assertThat(addressRepository.findAll().isEmpty()).isFalse();
	}
	
	@DisplayName("Find address by id from database")
	@Test
	public void findByIdFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Address address = new Address("Some street", "Some city", "Some state", "12345");
		
		//execute
		customerRepository.save(customer);
		
		address.setCustomer(customer);
		
		addressRepository.save(address);
		
		//then
		assertThat(addressRepository.findById(customer.getId()).isPresent()).isTrue();
	}
	
	@DisplayName("Verify that a address with false id doesn't exist")
	@Test
	public void verifyProductNotExist() {
		
		//assert
		assertThat(addressRepository.findById(1).isEmpty()).isTrue();
	}
	
	@DisplayName("Add new address to the database")
	@Test
	public void addNewAddressToDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Address expectedAddress = new Address("Some street", "Some city", "Some state", "12345");
		Address actualAddress;
		
		//execute
		customerRepository.save(customer);
		
		expectedAddress.setCustomer(customer);
		
		actualAddress = addressRepository.save(expectedAddress);
		
		//then
		assertThat(actualAddress).isEqualTo(expectedAddress);
	}
	
	@DisplayName("Delete address from database")
	@Test
	public void deleteFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Address address = new Address("Some street", "Some city", "Some state", "12345");
		
		//execute
		customer = customerRepository.save(customer);
		
		address.setCustomer(customer);
		
		addressRepository.save(address);
		addressRepository.delete(address);
		
		//then
		assertThat(addressRepository.findById(customer.getId()).isEmpty()).isTrue();
	}
}
