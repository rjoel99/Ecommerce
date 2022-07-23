/**
 * 
 */
package com.joel.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.entity.CurrencyCode;
import com.joel.model.ProductRequestModel;
import com.joel.model.ProductResponseModel;
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
	public static void setUp() {
		
		url = "http://localhost:8080/api/v1/products";
	}
	
	
	@DisplayName("Find all products with status 200")
	@Test
	public void findAll() throws Exception {
		
		//given
		Collection<ProductResponseModel> customers = List.of(ProductResponseModel.builder()
				.name("Laptop")
				.description("Laptop Dell 2020")
				.price(BigDecimal.valueOf(20000))
				.currencyCode(CurrencyCode.MXN)
				.build());
		
		//when
		Mockito.when(productService.findAll()).thenReturn(customers);
		
		//then
		mockMvc.perform(get(url)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
	}
	
	@DisplayName("Find product by id with status 200")
	@Test
	public void findById() throws Exception {
		
		//given
		int productId = 1;
		ProductResponseModel customer = ProductResponseModel.builder()
			.name("Laptop")
			.description("Laptop Dell 2020")
			.price(BigDecimal.valueOf(20000))
			.currencyCode(CurrencyCode.MXN)
			.build();
		
		//when
		Mockito.when(productService.findByIdAsModel(productId)).thenReturn(customer);
		
		//then
		mockMvc.perform(get(url + "/{product_id}", productId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("Laptop")))
			.andExpect(jsonPath("$.description", is("Laptop Dell 2020")))
			.andExpect(jsonPath("$.price", is(BigDecimal.valueOf(20000).intValue())))
			.andExpect(jsonPath("$.currencyCode", is(CurrencyCode.MXN.toString())));
	}
	
	@DisplayName("Add new product with status 201")
	@Test
	public void addNewProduct() throws JsonProcessingException, Exception {
		
		//given
		ProductRequestModel productReq = new ProductRequestModel("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);
		
		//when
		Mockito.doNothing().when(productService).add(productReq);
		
		//then
		mockMvc.perform(post(url)
				.content(new ObjectMapper().writeValueAsString(productReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
			.andExpect(jsonPath("$.message", is("Product added")));
	}
	
	@DisplayName("Update product by id with status 200")
	@Test
	public void updateById() throws JsonProcessingException, Exception {
		
		//given
		int productId = 1;
		ProductRequestModel productReq = new ProductRequestModel("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);

		//when
		Mockito.doNothing().when(productService).updateById(productId, productReq);
		
		//then
		mockMvc.perform(put(url + "/{product_id}", productId)
				.content(new ObjectMapper().writeValueAsString(productReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Product updated")));
	}
	
	@DisplayName("Delete product by id with status 200")
	@Test
	public void deleteById() throws Exception {
		
		//given
		int productId = 1;
		
		//when
		Mockito.doNothing().when(productService).deleteById(productId);
		
		//then
		mockMvc.perform(delete(url + "/{product_id}", productId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Product deleted")));
	}
}
