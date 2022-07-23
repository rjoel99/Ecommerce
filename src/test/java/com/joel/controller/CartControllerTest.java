package com.joel.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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

import com.joel.entity.CurrencyCode;
import com.joel.entity.Product;
import com.joel.model.CartResponseModel;
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
	public static void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/carts";
	}
	
	
	@DisplayName("Find cart by id with status 200")
	@Test
	public void findById() throws Exception {
	
		//given
		int customerId = 1;
		int cartId     = 1;
		List<Product> products = List.of(new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN));
		CartResponseModel cart = CartResponseModel.builder()
				.id(cartId)
				.products(products)
				.build();
		
		//when
		Mockito.when(cartService.findByIdAsModel(cartId)).thenReturn(cart);
		
		//then
		mockMvc.perform(get(url + "/{cart_id}", customerId, cartId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(cartId)));
	}
	
	@DisplayName("Add product to cart by id with status 201")
	@Test
	public void addProductToCartById() throws Exception {
		
		//given
		int customerId = 1;
		int cartId    = 1;
		int productId = 1;
		
		//when
		Mockito.doNothing().when(cartService).addProductToCart(cartId, productId);
		
		//then
		mockMvc.perform(post(url + "/{cart_id}", customerId, cartId)
				.queryParam("product", String.valueOf(productId))
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
			.andExpect(jsonPath("$.message", is("Product added to cart")));
	}
	
	@DisplayName("Remove product from cart by id with status 200")
	@Test
	public void removeProductFromCartById() throws Exception {
		
		//given
		int customerId = 1;
		int cartId    = 1;
		int productId = 1;
		
		//when
		Mockito.doNothing().when(cartService).removeProductFromCart(cartId, productId);
		
		//then
		mockMvc.perform(delete(url + "/{cart_id}", customerId, cartId)
				.queryParam("product", String.valueOf(productId))
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Product removed from cart")));
	}
}
