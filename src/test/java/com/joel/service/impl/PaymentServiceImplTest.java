package com.joel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

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

import com.joel.entity.CreditCard;
import com.joel.entity.Customer;
import com.joel.entity.Payment;
import com.joel.model.CreditCardRequestModel;
import com.joel.repository.PaymentRepository;
import com.joel.service.CustomerService;

/**
 * 
 * @author joel.rubio
 *
 */
@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

	@Mock
	private PaymentRepository paymentRepository;
	
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	@Spy
	private PaymentServiceImpl paymentService;
	
	
	@DisplayName("Find all payments")
	@Test
	public void findAll() {
		
		//given
		int customerId = 1;
		List<Payment> payments = List.of(new CreditCard("3243234354432321", "John Wick", 5, 2030, null));
		
		//when
		Mockito.when(paymentRepository.findAllByCustomerId(customerId)).thenReturn(payments);
		
		//execute
		paymentService.findAll(customerId);
		
		//then
		Mockito.verify(paymentRepository, Mockito.times(1)).findAllByCustomerId(customerId);
	}
	
	@DisplayName("Find payment by id")
	@Test
	public void findById() {
		
		//given
		int paymentId = 1;
		Optional<Payment> expectedPayment = Optional.of(new CreditCard("3243234354432321", "John Wick", 5, 2030, null));
		Payment actualPayment;
		
		//when
		Mockito.when(paymentRepository.findById(paymentId)).thenReturn(expectedPayment);
		
		//execute
		actualPayment = paymentService.findById(paymentId);
		
		//then
		assertEquals(expectedPayment.get(), actualPayment);
	}
	
	@DisplayName("Throw an exception if the id doesn't exist")
	@Test
	public void throwExceptionWithInvalidId() {
		
		//given
		int productId = 0;
		
		//when
		Mockito.when(paymentRepository.findById(productId)).thenThrow(new EntityNotFoundException("The id doesn't exist"));
		
		//then
		assertThrows(EntityNotFoundException.class, () -> paymentService.findById(productId));
	}
	
	@DisplayName("Add new payemnt")
	@Test
	public void addNewPayment() {
		
		//given
		int customerId = 1;
		Customer customer = new Customer("John", "Wick", "2343543423");
		CreditCardRequestModel paymentReq = new CreditCardRequestModel("3243234354432321", "John Wick", 5, 2030);
		Payment payment = new CreditCard("3243234354432321", "John Wick", 5, 2030, null);
		
		//when
		Mockito.when(customerService.findById(customerId)).thenReturn(customer);
		Mockito.when(paymentRepository.save((Payment) any(CreditCard.class))).thenReturn(payment);
		
		//execute
		paymentService.add(customerId, paymentReq);
		
		//then
		Mockito.verify(customerService, Mockito.times(1)).findById(customerId);
		Mockito.verify(paymentRepository, Mockito.times(1)).save((Payment) any(CreditCard.class));
	}
	
	@DisplayName("Update payment by id")
	@Test
	public void updateById() {
		
		//given
		int paymentId = 1;
		CreditCardRequestModel paymentReq = new CreditCardRequestModel("3243234354432321", "John Wick", 5, 2030);
		Payment payment = new CreditCard("3243234354432321", "John Wick", 5, 2030, null);
		
		//when
		Mockito.doReturn(payment).when(paymentService).findById(paymentId);
		Mockito.when(paymentRepository.save(payment)).thenReturn(payment);
		
		//execute
		paymentService.updateById(paymentId, paymentReq);
		
		//then
		Mockito.verify(paymentService, Mockito.times(1)).findById(paymentId);
		Mockito.verify(paymentRepository, Mockito.times(1)).save(payment);
	}
	
	@DisplayName("Delete payment by id")
	@Test
	public void deleteById() {
		
		//given
		int paymentId = 1;
		Payment payment = new CreditCard("3243234354432321", "John Wick", 5, 2030, null);
		
		//when
		Mockito.doReturn(payment).when(paymentService).findById(paymentId);
		Mockito.doNothing().when(paymentRepository).delete(payment);
		
		//execute
		paymentService.deleteById(paymentId);
		
		//then
		Mockito.verify(paymentService, Mockito.times(1)).findById(paymentId);
		Mockito.verify(paymentRepository, Mockito.times(1)).delete(payment);
	}
}
