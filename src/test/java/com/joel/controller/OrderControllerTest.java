package com.joel.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.joel.entity.OrderStatus;
import com.joel.model.OrderRequestModel;
import com.joel.model.OrderResponseModel;
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
	public static void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/carts/{cart_id}/orders";
	}
	
	
	@DisplayName("Find all orders with status 200")
	@Test
	public void findAll() throws Exception {
		
		//given
		int customerId = 1;
		int cartId     = 1;
		Collection<OrderResponseModel> orders = List.of(OrderResponseModel.builder()
				.id(1)
				.status(OrderStatus.CREATED)
				.createdOn(LocalDateTime.now())
				.build());
		
		//when
		Mockito.when(orderService.findAll(cartId)).thenReturn(orders);
		
		//then
		mockMvc.perform(get(url, customerId, cartId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
	}
	
	@DisplayName("Find order by id with status 200")
	@Test
	public void findById() throws Exception {
	
		//given
		int customerId = 1;
		int cartId     = 1;
		int orderId    = 1;
		OrderResponseModel order = OrderResponseModel.builder()
				.id(1)
				.status(OrderStatus.CREATED)
				.createdOn(LocalDateTime.now())
				.build();
		
		//when
		Mockito.when(orderService.findByIdAsModel(orderId)).thenReturn(order);
		
		//then
		mockMvc.perform(get(url + "/{order_id}", customerId, cartId, orderId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.status", is(OrderStatus.CREATED.toString())));
	}
	
	@DisplayName("Create new order with status 201")
	@Test
	public void createNewOrder() throws Exception {
		
		//given
		int customerId = 1;
		int cartId     = 1;
		int orderId    = 1;
		OrderResponseModel order = OrderResponseModel.builder()
				.id(orderId)
				.status(OrderStatus.CREATED)
				.createdOn(LocalDateTime.now())
				.build();
		
		//when
		Mockito.when(orderService.create(cartId)).thenReturn(order);
		
		//then
		mockMvc.perform(post(url, customerId, cartId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(orderId)))
			.andExpect(jsonPath("$.status", is(OrderStatus.CREATED.toString())));
	}
	
	@DisplayName("Update order by id with status 200")
	@Test
	public void updateById() throws Exception {
		
		//given
		int customerId = 1;
		int cartId     = 1;
		int orderId    = 1;
		OrderRequestModel orderReq = new OrderRequestModel(OrderStatus.PROCESSING);
		
		//when
		Mockito.doNothing().when(orderService).updateById(orderId, orderReq);
		
		//then
		mockMvc.perform(put(url + "/{order_id}", customerId, cartId, orderId)
				.content(new ObjectMapper().writeValueAsString(orderReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Order updated")));
	}
}
