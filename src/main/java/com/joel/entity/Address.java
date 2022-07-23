/**
 * 
 */
package com.joel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author joel.rubio
 *
 */
@Data
@NoArgsConstructor
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String addressLine;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String city;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String state;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String zipCode;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	
	public Address(String addressLine, String city, String state, String zipCode) {
		this.addressLine  = addressLine;
		this.city         = city;
		this.state        = state;
		this.zipCode      = zipCode;
	}
	
	public Address(String addressLine, String city, String state, String zipCode, Customer customer) {
		this(addressLine, city, state, zipCode);
		
		this.customer = customer;
	}
	
	public void addCustomer(Customer customer) {
		
		this.customer = customer;
		
		customer.addAddress(this);
	}
	
	public void removeCustomer() {
		
		this.customer.removeAddress(this);
		
		this.customer = null;
	}
}
