/**
 * 
 */
package com.joel.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.model.CustomerRequestModel;
import com.joel.model.CustomerResponseModel;
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
	public static void setUp() {
		
		url = "http://localhost:8080/api/v1/customers";
	}
	
	
	@DisplayName("Find all customers with status 200")
	@Test
	public void findAllWithoutPaginationAndSorting() throws Exception {
	
		//given
		Collection<CustomerResponseModel> customers = List.of(CustomerResponseModel.builder()
				.firstName("John")
				.lastName("Wick")
				.phone("2365765465")
				.build());
		
		//when
		Mockito.when(customerService.findAll(null, null, null)).thenReturn(customers);
		
		//then
		mockMvc.perform(get(url)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
	}
	
	@DisplayName("Find customer by id with status 200")
	@Test
	public void findById() throws Exception {
		
		//given
		int customerId = 1;
		CustomerResponseModel customer = CustomerResponseModel.builder()
				.firstName("John")
				.lastName("Wick")
				.phone("2365765465")
				.build();
		
		//when
		Mockito.when(customerService.findByIdAsModel(customerId)).thenReturn(customer);
		
		//then
		mockMvc.perform(get(url + "/{customer_id}", customerId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstName", is("John")))
			.andExpect(jsonPath("$.lastName", is("Wick")))
			.andExpect(jsonPath("$.phone", is("2365765465")));
	}
	
	@DisplayName("Add new customer with status 201")
	@Test
	public void addNewCustomer() throws Exception {
	
		//given
		CustomerRequestModel customerReq = new CustomerRequestModel("John", "Wick", "2343546554");
	
		//when
		Mockito.doNothing().when(customerService).add(customerReq);
		
		//then
		mockMvc.perform(post(url)
				.content(new ObjectMapper().writeValueAsString(customerReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
			.andExpect(jsonPath("$.message", is("Customer added")));
	}
	
	@DisplayName("Delete customer by id with status 200")
	@Test
	public void deleteById() throws Exception {
		
		//given
		int customerId = 1;
		
		//when
		Mockito.doNothing().when(customerService).deleteById(customerId);
		
		//then
		mockMvc.perform(delete(url + "/{customer_id}", customerId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Customer deleted")));
	}
}
