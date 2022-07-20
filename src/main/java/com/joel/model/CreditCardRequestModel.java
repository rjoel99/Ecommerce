package com.joel.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author joel.rubio
 *
 */
@Getter
@Setter
public class CreditCardRequestModel extends PaymentRequestModel {
	
	@NotNull
	@NotEmpty
	private String number;
	
	@NotNull
	@NotEmpty
	private String holder;
	
	@NotNull
	private Integer expireMonth;
	
	@NotNull
	private Integer expireYear;
	
	
	public boolean isEmpty() {
		
		return number == null && holder == null && 
			   expireMonth == null && expireYear == null;
	}
}
