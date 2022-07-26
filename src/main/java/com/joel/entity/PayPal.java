package com.joel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author joel.rubio
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class PayPal extends Payment {

	@Size(min = 16, max = 16)
	@Column(length = 16)
	private String number;
	
	private String email;
	
	public PayPal(String number, String email) {
		this.number = number;
		this.email = email;
	}
	
	public PayPal(String number, String email, Customer customer) {
		super(customer);
		this.number = number;
		this.email = email;
	}
}
