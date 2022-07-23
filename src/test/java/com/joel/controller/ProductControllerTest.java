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

import com.joel.service.ProductService;

/**
 * @author joel.rubio
 *
 */
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	private static String url;
	
	@BeforeAll()
	public void setUp() {
		
		url = "http://localhost:8080/api/v1/products";
	}
	
	
	@DisplayName("Find all products with status 200")
	@Test
	public void findAll() {
		
	}
	
	@DisplayName("Find product by id with status 200")
	@Test
	public void findById() {
		
	}
	
	@DisplayName("Add new product with status 201")
	@Test
	public void addNewProduct() {
		
	}
	
	@DisplayName("Update product by id with status 200")
	@Test
	public void updateById() {
		
	}
	
	@DisplayName("Delete product by id with status 200")
	@Test
	public void deleteById() {
		
	}
}
