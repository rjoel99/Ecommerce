package com.joel.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.joel.entity.CurrencyCode;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author joel.rubio
 *
 */
@Getter
@Setter
public class ProductRequestModel {

	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	private String description;
	
	@Min(value = 1)
	private BigDecimal price;
	
	@NotNull
	private CurrencyCode currencyCode;
}
