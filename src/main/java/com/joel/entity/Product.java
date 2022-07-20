package com.joel.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author joel.rubio
 *
 */
//@Data
//@Entity
//public class Product {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
//	@NotNull
//	@NotEmpty
//	@Column(nullable = false)
//	private String name;
//	
//	@NotNull
//	@NotEmpty
//	@Column(nullable = false)
//	private String description;
//	
//	@NotNull
//	@NotEmpty
//	@Column(nullable = false)
//	private BigDecimal price;
//	
//	@NotNull
//	@NotEmpty
//	@Column(nullable = false)
//	private String currency;
//	
//	@CreationTimestamp
//	@Column(name = "created_at", updatable = false)
//	private LocalDateTime createdAt;
//
//	@UpdateTimestamp
//	@Column(name = "updated_at")
//	private LocalDateTime updatedAt;
//	
//	@ToString.Exclude
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Cart cart;
//}
