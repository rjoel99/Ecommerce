package com.joel.model;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joel.entity.CreditCard;
import com.joel.entity.PayPal;
import com.joel.entity.Payment;

/**
 * @author joel.rubio
 *
 */
public abstract class PaymentResponseModel {

	private static Map<Class<? extends Payment>, Function<Payment, PaymentResponseModel>> paymentsAvailable;
	
	static {
		
		paymentsAvailable = Map.of(CreditCard.class, PaymentResponseModel::fromCreditCardToModel,
				  				   PayPal.class, PaymentResponseModel::fromPayPalToModel);
	}
	
	public static Collection<PaymentResponseModel> fromEntitiesToModels(Collection<Payment> payments) {
		
		return payments.stream()
				.map(p -> paymentsAvailable.get(p.getClass()).apply(p))
				.collect(Collectors.toList());
	}
	
	public static PaymentResponseModel fromEntityToModel(Payment payment) {
		
		return paymentsAvailable.get(payment.getClass())
				.apply(payment);
	}
	
	
	private static PaymentResponseModel fromCreditCardToModel(Payment payment) {
		
		CreditCard creditCard = (CreditCard) payment;
		
		return CreditCardResponseModel.builder()
				.number(creditCard.getNumber())
				.holder(creditCard.getHolder())
				.expireMonth(creditCard.getExpireMonth())
				.expireYear(creditCard.getExpireYear())
				.build();
	}
	
	private static PaymentResponseModel fromPayPalToModel(Payment payment) {
		
		PayPal payPal = (PayPal) payment;
		
		return PayPalResponseModel.builder()
				.number(payPal.getNumber())
				.email(payPal.getEmail())
				.build();
	}
}
