package com.joel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.joel.entity.CreditCard;
import com.joel.entity.Customer;
import com.joel.entity.PayPal;
import com.joel.entity.Payment;

/**
 * @author joel.rubio
 *
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PaymentRepositoryTest {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@DisplayName("Injected component repository is not null")
	@Test
	public void componentRepositoryNotNull() {
		
		assertThat(paymentRepository).isNotNull();
		assertThat(customerRepository).isNotNull();
	}
	
	@DisplayName("Find all payments from database")
	@Test
	public void findAllFromDatabase() {
		
		//given
		Customer customer1 = new Customer("John", "Wick", "4354654323");
		Customer customer2 = new Customer("Margot", "Robbie", "6778895645");
		Payment payment1 = new CreditCard("3243544323122343", "John Wick", 5, 2030);
		Payment payment2 = new PayPal("2345546554433212", "margot@email.com");
		
		//execute
		customerRepository.saveAll(List.of(customer1, customer2));
		
		payment1.setCustomer(customer1);
		payment2.setCustomer(customer2);
		
		paymentRepository.saveAll(List.of(payment1, payment2));
		
		//then
		assertThat(paymentRepository.findAll().isEmpty()).isFalse();
	}
	
	@DisplayName("Find payment by id from database")
	@Test
	public void findByIdFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Payment payment = new CreditCard("3243544323122343", "John Wick", 5, 2030);
		
		//execute
		customerRepository.save(customer);
		
		payment.setCustomer(customer);
		
		paymentRepository.save(payment);
		
		//then
		assertThat(paymentRepository.findById(payment.getId()).isPresent()).isTrue();
	}
	
	@DisplayName("Verify that a payment with false id doesn't exist")
	@Test
	public void verifyProductNotExist() {
		
		//assert
		assertThat(paymentRepository.findById(1).isEmpty()).isTrue();
	}
	
	@DisplayName("Add new payment to the database")
	@Test
	public void addNewPaymentToDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Payment expectedPayment = new CreditCard("3243544323122343", "John Wick", 5, 2030);
		Payment actualPayment;
		
		//execute
		customerRepository.save(customer);
		
		expectedPayment.setCustomer(customer);
		
		actualPayment = paymentRepository.save(expectedPayment);
		
		//then
		assertThat(actualPayment).isEqualTo(expectedPayment);
	}
	
	@DisplayName("Delete payment from database")
	@Test
	public void deleteFromDatabase() {
		
		//given
		Customer customer = new Customer("John", "Wick", "4354654323");
		Payment payment = new CreditCard("3243544323122343", "John Wick", 5, 2030);
		
		//execute
		customer = customerRepository.save(customer);
		
		payment.setCustomer(customer);
		
		paymentRepository.save(payment);
		paymentRepository.delete(payment);
		
		//then
		assertThat(paymentRepository.findById(customer.getId()).isEmpty()).isTrue();
	}
}
