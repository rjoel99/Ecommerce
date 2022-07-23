package com.joel.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class CustomerRequestModel {

	@NotNull
	@NotEmpty
	private String firstName;
	
	@NotNull
	@NotEmpty
	private String lastName;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "(\\(\\d{3}\\)[.-]?|\\d{3}[.-]?)?\\d{3}[.-]?\\d{4}")
	private String phone;
}
