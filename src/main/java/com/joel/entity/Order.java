package com.joel.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author joel.rubio
 *
 */
//@Data
//@Entity
//public class Order {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
//	@CreationTimestamp
//	@Column(name = "created_at", updatable = false)
//	private LocalDateTime createdAt;
//	
//	@Enumerated(EnumType.STRING)
//	@Column(nullable = false)
//	private OrderStatus status;
//	
//	@ToString.Exclude
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "cart_id")
//	private Cart cart;
//}
