package com.joel.entity;

import java.time.LocalDateTime;
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

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToMany
	@JoinTable(name = "cart_item", 
			   joinColumns        = @JoinColumn(name = "cart_id"),
			   inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cart")
	private List<Order> orders;
	
	
	public Cart(Customer customer) {
		this.customer   = customer;
	}
	
	public boolean areThereAnyProducts() {
		return products.isEmpty();
	}
	
	public void addProduct(Product product) {
		
		this.products.add(product);
		
		product.addCart(this);
	}
	
	public void removeProduct(Product product) {
		this.products.remove(product);
	}
	
	public void removeFromProduct(Product product) {
		
		removeProduct(product);
		
		product.removeCart(this);
	}
	
	public void addOrder(Order order) {
		
		this.orders.add(order);
		
		order.addCart(this);
	}
}
