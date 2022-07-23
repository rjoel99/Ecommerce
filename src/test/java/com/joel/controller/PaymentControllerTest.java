/**
 * 
 */
package com.joel.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.joel.service.PaymentService;

/**
 * @author joel.rubio
 *
 */
@WebMvcTest(controllers = PaymentController.class)
public class PaymentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PaymentService paymentService;
	
	private static String url;
	
	@BeforeAll()
	public void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/payments";
	}
	
	
	@DisplayName("Find all payments with status 200")
	@Test
	public void findAll() {
		
	}
	
	@DisplayName("Find payment by id with status 200")
	@Test
	public void findById() {
		
	}
	
	@DisplayName("Add new payment with status 201")
	@Test
	public void addNewPayment() {
		
	}
	
	@DisplayName("Update all attributes of a payment by id with status 200")
	@Test
	public void updateAllById() {
		
	}
	
	@DisplayName("Update some attributes of a payment by id with status 200")
	@Test
	public void updateSomeById() {
		
	}
	
	@DisplayName("Delete payment by id with status 200")
	@Test
	public void deleteById() {
		
	}
}
