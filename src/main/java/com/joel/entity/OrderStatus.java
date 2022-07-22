package com.joel.entity;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
	FAILED;
	
	private static Map<String, OrderStatus> values;
	
	static {
		
		values = new HashMap<>();
		
		values.put("CREATED", CREATED);
		values.put("PROCESSING", PROCESSING);
		values.put("PENDING_PAYMENT", PENDING_PAYMENT);
		values.put("PARTIALLY_SHIPPED", PARTIALLY_SHIPPED);
		values.put("SHIPPED", SHIPPED);
		values.put("COMPLETED", COMPLETED);
		values.put("CANCELLED", CANCELLED);
		values.put("FAILED", FAILED);
	}
	
	@JsonCreator
	public static OrderStatus fromStringToOrderStatus(String status) {
		
		OrderStatus response = values.get(status.toUpperCase());
		
		//update to not throw in the json thread
		if (response == null)
			throw new IllegalArgumentException("The status doesn't exist");
		
		return response;
	}
	
	@JsonValue
	public static String fromOrderStatusToString(OrderStatus status) {
		
		return values.entrySet()
			.stream()
			.filter(set -> set.getValue() == status)
			.map(set -> set.getKey())
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("There is no mapping for the status order"));
	}
}
