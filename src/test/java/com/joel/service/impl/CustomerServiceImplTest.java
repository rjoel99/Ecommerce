package com.joel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joel.entity.Customer;
import com.joel.model.CustomerRequestModel;
import com.joel.repository.CustomerRepository;
import com.joel.service.CartService;

/**
 * @author joel.rubio
 *
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private CartService cartService;
	
	@InjectMocks
	@Spy
	private CustomerServiceImpl customerService;
	
	@DisplayName("Find all customers")
	@Test
	public void findAllWithoutSort() {
		
		//given
		@SuppressWarnings("unchecked")
		Page<Customer> customers = Mockito.mock(Page.class);
		
		//when
		Mockito.when(customerRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(customers);
		
		//execute
		customerService.findAll(0, 5, null);
		
		//then
		Mockito.verify(customerRepository, Mockito.times(1)).findAll(org.mockito.ArgumentMatchers.isA(Pageable.class));
	}
	
	@DisplayName("Find customer by id")
	@Test
	public void findById() {
		
		//given
		int customerId = 1;
		Optional<Customer> expectedCustomer = Optional.of(new Customer("John", "Wick", "435456765"));
		Customer actualCustomer;
		
		//when
		Mockito.when(customerRepository.findById(customerId)).thenReturn(expectedCustomer);
		
		//execute
		actualCustomer = customerService.findById(customerId);
		
		//then
		assertEquals(expectedCustomer.get(), actualCustomer);
	}
	
	@DisplayName("Throw an exception if the id doesn't exist")
	@Test
	public void throwExceptionWithInvalidId() {
		
		//given
		int customerId = 0;
		
		//when
		Mockito.when(customerRepository.findById(customerId)).thenThrow(new EntityNotFoundException("The id doesn't exist"));
		
		//then
		assertThrows(EntityNotFoundException.class, () -> customerService.findById(customerId));
	}
	
	@DisplayName("Add new customer")
	@Test
	public void addNewCustomer() {
		
		//given
		CustomerRequestModel customerReq = new CustomerRequestModel("John", "Wick", "3254658765");
		Customer customer = new Customer("John", "Wick", "3254658765");
		
		//when
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		Mockito.doNothing().when(cartService).create(customer);
		
		//execute
		customerService.add(customerReq);
		
		//then
		Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
		Mockito.verify(cartService, Mockito.times(1)).create(customer);
	}
	
	@DisplayName("Update customer by id")
	@Test
	public void updateById() {
		
		//given
		int customerId = 1;
		CustomerRequestModel customerReq = new CustomerRequestModel("John", "Wick", "2343454354");
		Customer customer = new Customer("John", "Wick", "2343454354");
		
		//when
		Mockito.doReturn(customer).when(customerService).findById(customerId);
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		
		//execute
		customerService.updateById(customerId, customerReq);
		
		//then
		Mockito.verify(customerService, Mockito.times(1)).findById(customerId);
		Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
	}
	
	@DisplayName("Delete customer by id")
	@Test
	public void deleteById() {
		
		//given
		int customerId = 1;
		Customer customer =  new Customer("John", "Wick", "2343454354");
		
		//when
		Mockito.doReturn(customer).when(customerService).findById(customerId);
		Mockito.doNothing().when(customerRepository).delete(customer);
		
		//execute
		customerService.deleteById(customerId);
		
		//then
		Mockito.verify(customerService, Mockito.times(1)).findById(customerId);
		Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
	}
}
