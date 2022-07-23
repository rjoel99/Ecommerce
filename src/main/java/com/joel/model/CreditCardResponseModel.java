package com.joel.model;

import lombok.Builder;
import lombok.Getter;

/**
 * 
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class CreditCardResponseModel extends PaymentResponseModel {

	private String number;
	private String holder;
	private Integer expireMonth;
	private Integer expireYear;
}
