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
public enum CurrencyCode {

	USD, 
	EUR, 
	MXN;
	
	private static Map<String, CurrencyCode> codes;
	
	static {
		
		codes = new HashMap<>();
	
		codes.put("USD", USD);
		codes.put("EUR", EUR);
		codes.put("MXN", MXN);
	}
	
	@JsonCreator
	public static CurrencyCode fromStringToCurrencyCode(String value) {
		
		CurrencyCode response = codes.get(value.toUpperCase());
		
		if (response == null)
			throw new IllegalArgumentException("The currency code doesn't exist");
	
		return response;
	}
	
	@JsonValue
	public static String fromCurrencyCodeToString(CurrencyCode currencyCode) {
		
		return codes.entrySet()
			.stream()
			.filter(s -> s.getValue() == currencyCode)
			.map(s -> s.getKey())
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("There is no mapping for the currency code"));
	}
}
