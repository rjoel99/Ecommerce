package com.joel.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author joel.rubio
 *
 */
//@Data
//@Entity
//public class Cart {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
//	@NotNull
//	@Column(name = "total_price", nullable = false)
//	private BigDecimal totalPrice;
//	
//	@OneToOne
//	@JoinColumn(name = "customer_id")
//	private Customer customer;
//	
//	@OneToMany(mappedBy = "product")
//	private List<Product> products;
//	
//	@OneToMany(mappedBy = "cart")
//	private List<Order> orders;
//	
//	
//	public void addProduct(Product product) {
//		
//		this.products.add(product);
//	}
//	
//	public void addOrder(Order order) {
//		
//		this.orders.add(order);
//	}
//}
