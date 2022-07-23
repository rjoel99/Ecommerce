/**
 * 
 */
package com.joel.model;

import java.math.BigDecimal;

import com.joel.entity.CurrencyCode;

import lombok.Builder;
import lombok.Getter;

/**
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class ProductResponseModel {

	private int id;
	private String name;
	private String description;
	private BigDecimal price;
	private CurrencyCode currencyCode;
}
