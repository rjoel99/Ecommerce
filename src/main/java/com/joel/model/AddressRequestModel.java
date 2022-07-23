package com.joel.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author joel.rubio
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class AddressRequestModel {

	@NotNull(message = "Address line can't be null")
	@NotEmpty(message = "Address line can't be empty")
	private String addressLine;
	
	@NotNull(message = "City can't be null")
	@NotEmpty(message = "City can't be empty")
	private String city;
	
	@NotNull(message = "State can't be null")
	@NotEmpty(message = "State can't be empty")
	private String state;
	
	@NotNull(message = "Zip code can't be null")
	@NotEmpty(message = "Zip code can't be empty")
	private String zipCode;
}
