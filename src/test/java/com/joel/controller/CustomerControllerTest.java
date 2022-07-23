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

import com.joel.service.CustomerService;

/**
 * @author joel.rubio
 *
 */
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	private static String url;
	
	@BeforeAll()
	public void setUp() {
		
		url = "http://localhost:8080/api/v1/customers";
	}
	
	
	@DisplayName("Find all customers with status 200")
	@Test
	public void findAll() {
		
	}
	
	@DisplayName("Find customer by id with status 200")
	@Test
	public void findById() {
		
	}
	
	@DisplayName("Add new customer with status 201")
	@Test
	public void addNewCustomer() {
		
	}
	
	@DisplayName("Delete customer by id with status 200")
	@Test
	public void deleteById() {
		
	}
}
