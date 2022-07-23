package com.joel.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String name;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String description;
	
	@Min(value = 1)
	@Column(nullable = false)
	private BigDecimal price;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "currency_code")
	private CurrencyCode currencyCode;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@ToString.Exclude
	@ManyToMany(mappedBy = "products")
	private List<Cart> carts;
	
	
	public Product(String name, String description, BigDecimal price, CurrencyCode currencyCode) {
		this.name         = name;
		this.description  = description;
		this.price        = price;
		this.currencyCode = currencyCode;
	}
	
	public void addCart(Cart cart) {
		this.carts.add(cart);
	}
	
	public void removeFromCart() {
		
		this.carts.forEach(cart -> cart.removeProduct(this));
		
		this.carts.clear();
	}
	
	public void removeCart(Cart cart) {
		this.carts.remove(cart);
	}
}
