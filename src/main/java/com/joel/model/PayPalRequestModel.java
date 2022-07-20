/**
 * 
 */
package com.joel.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author joel.rubio
 *
 */
@Getter
@Setter
public class PayPalRequestModel extends PaymentRequestModel {
	
	@NotNull
	@NotEmpty
	private String number;
	
	@NotNull
	@NotEmpty
	private String email;
	
	
	public boolean isEmpty() {
		
		return number == null && email == null;
	}
}
