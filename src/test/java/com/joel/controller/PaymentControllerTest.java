package com.joel.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.model.CreditCardRequestModel;
import com.joel.model.CreditCardResponseModel;
import com.joel.model.PaymentRequestModel;
import com.joel.model.PaymentResponseModel;
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
	public static void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/payments";
	}
	
	
	@DisplayName("Find all payments with status 200")
	@Test
	public void findAll() throws Exception {
		
		//given
		int customerId = 1;
		Collection<PaymentResponseModel> customers = List.of(CreditCardResponseModel.builder()
				.number("3243546554435443")
				.holder("John Wick")
				.expireMonth(5)
				.expireYear(2030)
				.build());
		
		//when
		Mockito.when(paymentService.findAll(customerId)).thenReturn(customers);
		
		//then
		mockMvc.perform(get(url, customerId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
	}
	
	@DisplayName("Find payment by id with status 200")
	@Test
	public void findById() throws Exception {
		
		//given
		int customerId = 1;
		int paymentId  = 1;
		PaymentResponseModel payment = CreditCardResponseModel.builder()
				.number("3243546554435443")
				.holder("John Wick")
				.expireMonth(5)
				.expireYear(2030)
				.build();
		
		//when
		Mockito.when(paymentService.findByIdAsModel(paymentId)).thenReturn(payment);
		
		//then
		mockMvc.perform(get(url + "/{payment_id}", customerId, paymentId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.number", is("3243546554435443")))
			.andExpect(jsonPath("$.holder", is("John Wick")))
			.andExpect(jsonPath("$.expireMonth", is(5)))
			.andExpect(jsonPath("$.expireYear", is(2030)));
	}
	
	@DisplayName("Add new payment with status 201")
	@Test
	public void addNewPayment() throws JsonProcessingException, Exception {
	
		//given
		int customerId = 1;
		PaymentRequestModel paymentReq = new CreditCardRequestModel("3243546554435443", "John Wick", 5, 2030);
	
		//when
		Mockito.doNothing().when(paymentService).add(customerId, paymentReq);
		
		//then
		mockMvc.perform(post(url, customerId)
				.content(new ObjectMapper().writeValueAsString(paymentReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
			.andExpect(jsonPath("$.message", is("Payment created")));
	}
	
	@DisplayName("Update all attributes of a payment by id with status 200")
	@Test
	public void updateAllById() throws JsonProcessingException, Exception {
		
		//given
		int customerId = 1;
		int paymentId  = 1;
		PaymentRequestModel paymentReq = new CreditCardRequestModel("3243546554435443", "John Wick", 5, 2030);
	
		//when
		Mockito.doNothing().when(paymentService).add(customerId, paymentReq);
		
		//then
		mockMvc.perform(put(url + "/{payment_id}", customerId, paymentId)
				.content(new ObjectMapper().writeValueAsString(paymentReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Payment updated")));
	}
	
	@DisplayName("Update some attributes of a payment by id with status 200")
	@Test
	public void updateSomeById() throws JsonProcessingException, Exception {
	
		//given
		int customerId = 1;
		int paymentId  = 1;
		PaymentRequestModel paymentReq = new CreditCardRequestModel("3243546554435443", "John Wick", 5, 2030);
	
		//when
		Mockito.doNothing().when(paymentService).add(customerId, paymentReq);
		
		//then
		mockMvc.perform(patch(url + "/{payment_id}", customerId, paymentId)
				.content(new ObjectMapper().writeValueAsString(paymentReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Payment updated")));
	}
	
	@DisplayName("Delete payment by id with status 200")
	@Test
	public void deleteById() throws Exception {
		
		//given
		int customerId = 1;
		int paymentId  = 1;
		
		//when
		Mockito.doNothing().when(paymentService).deleteById(paymentId);
		
		//then
		mockMvc.perform(delete(url + "/{payment_id}", customerId, paymentId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Payment deleted")));
	}
}
