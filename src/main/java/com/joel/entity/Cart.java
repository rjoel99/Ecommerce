package com.joel.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author joel.rubio
 *
 */
@Data
@NoArgsConstructor
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;
	
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToMany
	@JoinTable(name = "cart_item", 
			   joinColumns = @JoinColumn(name = "cart_id"),
			   inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;
	
	@OneToMany(mappedBy = "cart")
	private List<Order> orders;
	
	
	public Cart(BigDecimal totalPrice, Customer customer) {
		this.totalPrice = totalPrice;
		this.customer   = customer;
	}
	
	public void addProduct(Product product) {
		
		this.products.add(product);
		
		product.addCart(this);
	}
	
	public void addOrder(Order order) {
		
		this.orders.add(order);
		
		order.addCart(this);
	}
}
