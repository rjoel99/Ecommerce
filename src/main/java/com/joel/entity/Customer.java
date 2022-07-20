package com.joel.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author joel.rubio
 *
 */
@Data
@NoArgsConstructor
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private int id;
	
	@NotNull
	@NotEmpty
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotNull
	@NotEmpty
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "(\\(\\d{3}\\)[.-]?|\\d{3}[.-]?)?\\d{3}[.-]?\\d{4}")
	private String phone;
	
	@JsonIgnore
	@ToString.Exclude
	@OneToMany
	private List<Payment> payments;
	
	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "customer")
	private List<Address> addresses;
	
//	@OneToOne(fetch = FetchType.LAZY)
//	private Cart cart;
	
	
	public Customer(String fistName, String lastName, String phone) {
		this.firstName = fistName;
		this.lastName  = lastName;
		this.phone     = phone;
	}
	
	public void addPayment(Payment payment) {
		this.payments.add(payment);
	}
	
	public void removePayment(Payment payment) {
		this.payments.remove(payment);
	}
	
	public void addAddress(Address address) {
		
		addresses.add(address);
	}
	
	public void removeAddress(Address address) {
		
		addresses.remove(address);
	}
}
