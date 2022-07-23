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
import com.joel.model.AddressRequestModel;
import com.joel.model.AddressResponseModel;
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
	public static void setUp() {
		
		url = "http://localhost:8080/api/v1/customers/{customer_id}/addresses";
	}
	
	
	@DisplayName("Find all addresses with status 200")
	@Test
	public void findAll() throws Exception {
		
		//given
		int customerId = 1;
		Collection<AddressResponseModel> addresses = List.of(AddressResponseModel.builder()
				.addressLine("Some street")
				.city("Some city")
				.state("Some state")
				.zipCode("43544")
				.build());
		
		//when
		Mockito.when(addressService.findAll(customerId, null, null, null)).thenReturn(addresses);
		
		//then
		mockMvc.perform(get(url, customerId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
	}
	
	@DisplayName("Find address by id with status 200")
	@Test
	public void findById() throws Exception {
	
		//given
		int customerId = 1;
		int addressId  = 1;
		AddressResponseModel address = AddressResponseModel.builder()
				.addressLine("Some street")
				.city("Some city")
				.state("Some state")
				.zipCode("43544")
				.build();
		
		//when
		Mockito.when(addressService.findByIdAsModel(addressId)).thenReturn(address);
		
		//then
		mockMvc.perform(get(url + "/{address_id}", customerId, addressId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.addressLine", is("Some street")))
			.andExpect(jsonPath("$.city", is("Some city")))
			.andExpect(jsonPath("$.state", is("Some state")))
			.andExpect(jsonPath("$.zipCode", is("43544")));
	}
	
	@DisplayName("Add new address with status 201")
	@Test
	public void addNewAddress() throws JsonProcessingException, Exception {
	
		//given
		int customerId = 1;
		AddressRequestModel addressReq = new AddressRequestModel("Some street", "Some city", "Some state", "23432");
	
		//when
		Mockito.doNothing().when(addressService).add(customerId, addressReq);
		
		//then
		mockMvc.perform(post(url, customerId)
				.content(new ObjectMapper().writeValueAsString(addressReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
			.andExpect(jsonPath("$.message", is("Address added")));
	}
	
	@DisplayName("Update all attributes of an address by id with status 200")
	@Test
	public void updateAllById() throws JsonProcessingException, Exception {
		
		//given
		int customerId = 1;
		int addressId  = 1;
		AddressRequestModel addressReq = new AddressRequestModel("Some street", "Some city", "Some state", "23432");
	
		//when
		Mockito.doNothing().when(addressService).add(addressId, addressReq);
		
		//then
		mockMvc.perform(put(url + "/{address_id}", customerId, addressId)
				.content(new ObjectMapper().writeValueAsString(addressReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Address updated")));
	}
	
	@DisplayName("Update some attributes of an address by id with status 200")
	@Test
	public void updateSomeById() throws JsonProcessingException, Exception {
		
		//given
		int customerId = 1;
		int addressId  = 1;
		AddressRequestModel addressReq = new AddressRequestModel("Some street", "Some city", "Some state", "23432");
	
		//when
		Mockito.doNothing().when(addressService).add(addressId, addressReq);
		
		//then
		mockMvc.perform(patch(url + "/{address_id}", customerId, addressId)
				.content(new ObjectMapper().writeValueAsString(addressReq))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Address updated")));
	}
	
	@DisplayName("Delete address by id with status 200")
	@Test
	public void deleteById() throws Exception {
		
		//given
		int customerId = 1;
		int addressId  = 1;
		
		//when
		Mockito.doNothing().when(addressService).deleteById(addressId);
		
		//then
		mockMvc.perform(delete(url + "/{address_id}", customerId, addressId)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
			.andExpect(jsonPath("$.message", is("Address deleted")));
	}
}
