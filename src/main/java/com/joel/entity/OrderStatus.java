package com.joel.entity;

/**
 * 
 * @author joel.rubio
 *
 */
public enum OrderStatus {

	CREATED, 
	PROCESSING,
	PENDING_PAYMENT,
	PARTIALLY_SHIPPED,
	SHIPPED,
	COMPLETED,
	CANCELLED,
	FAILED
}
