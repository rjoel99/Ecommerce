package com.joel.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class PayPalResponseModel extends PaymentResponseModel {

	private String number;
	private String email;
}
