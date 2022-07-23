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

import com.joel.service.AddressService;

/**
 * @author joel.rubio
 *
 */
@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AddressService addressService;
	
	private static String url;
	
	@BeforeAll()
	public void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/addresses";
	}
	
	
	@DisplayName("Find all addresses with status 200")
	@Test
	public void findAll() {
		
	}
	
	@DisplayName("Find address by id with status 200")
	@Test
	public void findById() {
		
	}
	
	@DisplayName("Add new address with status 201")
	@Test
	public void addNewAddress() {
		
	}
	
	@DisplayName("Update all attributes of an address by id with status 200")
	@Test
	public void updateAllById() {
		
	}
	
	@DisplayName("Update some attributes of an address by id with status 200")
	@Test
	public void updateSomeById() {
		
	}
	
	@DisplayName("Delete address by id with status 200")
	@Test
	public void deleteById() {
		
	}
}
