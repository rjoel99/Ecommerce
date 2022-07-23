package com.joel.controller;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.joel.message.ErrorMessage;
import com.joel.message.SuccessMessage;
import com.joel.model.AddressPatchRequestModel;
import com.joel.model.AddressRequestModel;
import com.joel.model.AddressResponseModel;
import com.joel.service.AddressService;

/**
 * 
 * @author joel.rubio
 *
 */
@RestController
@RequestMapping("/api/v1/customers/{customer_id}/addresses")
public class AddressController {

	private AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<AddressResponseModel>> getAll(@PathVariable("customer_id") int customerId,
													  @RequestParam(defaultValue = "0", required = false) Integer page,
													  @RequestParam(defaultValue = "5", required = false) Integer size,
													  @RequestParam(required = false) String[] sort) {
		
		Collection<AddressResponseModel> addresses = addressService.findAll(customerId, page, size, sort);
		
		return ResponseEntity.ok(addresses);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressResponseModel> getById(@PathVariable int id) {
		
		AddressResponseModel address = addressService.findByIdAsModel(id);
		
		return ResponseEntity.ok(address);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> add(@PathVariable("customer_id") int customerId, @Valid @RequestBody AddressRequestModel addressRequestModel) {
		
		addressService.add(customerId, addressRequestModel);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessMessage.builder()
						.status(HttpStatus.OK.value())
						.message("Address added")
						.datetime(LocalDateTime.now())
						.build());
	}
	
	@PatchMapping(path = "/{address_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateSomeById(@PathVariable("address_id") int addressId,
												 @Valid @RequestBody AddressPatchRequestModel addressRequestModel,
												 WebRequest request) {
		
		if (addressRequestModel.isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ErrorMessage.builder()
							.status(HttpStatus.BAD_REQUEST.value())
							.message("There need to be at least one property")
							.datetime(LocalDateTime.now())
							.path(((ServletWebRequest) request).getRequest().getRequestURI()));
		
		addressService.updateSomeById(addressId, addressRequestModel);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Address updated")
				.datetime(LocalDateTime.now())
				.build());
	}
	
	@PutMapping(path = "/{address_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateAllById(@PathVariable("address_id") int addressId, @Valid @RequestBody AddressRequestModel addressRequestModel) {
		
		addressService.updateAllById(addressId, addressRequestModel);

		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Address updated")
				.datetime(LocalDateTime.now())
				.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable int id) {
		
		addressService.deleteById(id);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Address deleted")
				.datetime(LocalDateTime.now())
				.build());
	}
}
