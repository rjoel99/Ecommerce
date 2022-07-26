package com.joel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class CreditCard extends Payment {

	@NotNull
	@Size(min = 16, max = 16)
	@Column(length = 16)
	private String number;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String holder;
	
	@NotNull
	@Column(name = "expire_month")
	private int expireMonth;
	
	@NotNull
	@Column(name = "expire_year")
	private int expireYear;
	
	public CreditCard(String number, String holder, int expireMonth, int expireYear) {
		this.number = number;
		this.holder = holder;
		this.expireMonth = expireMonth;
		this.expireYear = expireYear;
	}
	
	public CreditCard(String number, String holder, int expireMonth, int expireYear, Customer customer) {
		super(customer);
		this.number = number;
		this.holder = holder;
		this.expireMonth = expireMonth;
		this.expireYear = expireYear;
	}
}
