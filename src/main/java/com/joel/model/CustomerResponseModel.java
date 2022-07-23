package com.joel.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class CustomerResponseModel {

	private int id;
	private String firstName;
	private String lastName;
	private String phone;
}
