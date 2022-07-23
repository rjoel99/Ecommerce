/**
 * 
 */
package com.joel.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class AddressResponseModel {

	private int id;
	private String addressLine;
	private String city;
	private String state;
	private String zipCode;
}
