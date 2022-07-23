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

import com.joel.service.CartService;

/**
 * @author joel.rubio
 *
 */
@WebMvcTest(controllers = CartController.class)
public class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CartService cartService;
	
	private static String url;
	
	@BeforeAll()
	public void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/carts";
	}
	
	
	@DisplayName("Find cart by id with status 200")
	@Test
	public void findById() {
		
	}
	
	@DisplayName("Add product to cart by id with status 201")
	@Test
	public void addProductToCartById() {
		
	}
	
	@DisplayName("Remove product from cart by id with status 200")
	@Test
	public void removeProductFromCartById() {
		
	}
}
