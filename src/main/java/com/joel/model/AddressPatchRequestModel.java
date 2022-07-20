package com.joel.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author joel.rubio
 *
 */
@Getter
@Setter
public class AddressPatchRequestModel {
	
	private String addressLine;
	private String city;
	private String state;
	private String zipCode;
	
	public boolean isEmpty() {
		
		return addressLine == null && city == null && 
			   state == null && zipCode == null;
	}
}
