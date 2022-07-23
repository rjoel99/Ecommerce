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

import com.joel.service.OrderService;

/**
 * @author joel.rubio
 *
 */
@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OrderService orderService;
	
	private static String url;
	
	@BeforeAll()
	public void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/carts/{cart_id}/orders";
	}
	
	
	@DisplayName("Find all orders with status 200")
	@Test
	public void findAll() {
		
	}
	
	@DisplayName("Find order by id with status 200")
	@Test
	public void findById() {
		
	}
	
	@DisplayName("Create new order with status 201")
	@Test
	public void createNewOrder() {
		
	}
	
	@DisplayName("Update order by id with status 200")
	@Test
	public void updateById() {
		
	}
}
