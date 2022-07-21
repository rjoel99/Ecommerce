/**
 * 
 */
package com.joel.model;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.joel.entity.CreditCard;
import com.joel.entity.Customer;
import com.joel.entity.PayPal;
import com.joel.entity.Payment;

/**
 * @author joel.rubio
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	
	@Type(value = CreditCardRequestModel.class, name = "creditcard"),
	@Type(value = PayPalRequestModel.class, name = "paypal")
})
public abstract class PaymentRequestModel {

	public abstract boolean isEmpty();
	
	
	public static Map<Class<? extends PaymentRequestModel>, BiFunction<PaymentRequestModel, Customer, Payment>> getPaymentsToCreate() {
		
		return Map.of(CreditCardRequestModel.class, PaymentRequestModel::createCreditCard,
					  PayPalRequestModel.class, PaymentRequestModel::createPayPal);
	}
	
	public static Map<Class<? extends PaymentRequestModel>, BiConsumer<Payment, PaymentRequestModel>> getPaymentsToUpdate() {
		
		return Map.of(CreditCardRequestModel.class, PaymentRequestModel::updateCreditCard,
					  PayPalRequestModel.class, PaymentRequestModel::updatePayPal);
	}
	
	
	public static Payment createCreditCard(PaymentRequestModel paymentReq, Customer customer) {
		
		CreditCardRequestModel creditCardReq = (CreditCardRequestModel) paymentReq;
		
		return new CreditCard(creditCardReq.getNumber(), creditCardReq.getHolder(), 
							  creditCardReq.getExpireMonth(), creditCardReq.getExpireYear(), customer);
	}
	
	public static Payment createPayPal(PaymentRequestModel paymentReq, Customer customer) {
		
		PayPalRequestModel payPalReq = (PayPalRequestModel) paymentReq;
		
		return new PayPal(payPalReq.getNumber(), payPalReq.getEmail(), customer);
	}
	
	
	public static void updateCreditCard(Payment payment, PaymentRequestModel paymentReq) {
		
		if (!(payment instanceof CreditCard))
			throw new ClassCastException("The id of payment is incorrect");
		
		CreditCardRequestModel creditCardReq = (CreditCardRequestModel) paymentReq;
		
		if (creditCardReq.isEmpty())
			throw new IllegalArgumentException("There need to be at least one field for the type of payment");
		
		
		if (creditCardReq.getNumber() != null)
			((CreditCard) payment).setNumber(creditCardReq.getNumber());
		
		if (creditCardReq.getHolder() != null)
			((CreditCard) payment).setHolder(creditCardReq.getHolder());
		
		if (creditCardReq.getExpireMonth() != null)
			((CreditCard) payment).setExpireMonth(creditCardReq.getExpireMonth());
		
		if (creditCardReq.getExpireYear() != null)
			((CreditCard) payment).setExpireYear(creditCardReq.getExpireYear());
	}
	
	
	public static void updatePayPal(Payment payment, PaymentRequestModel paymentReq) {
		
		if (!(payment instanceof PayPal))
			throw new ClassCastException("The id of payment is incorrect");
		
		PayPalRequestModel payPalReq = (PayPalRequestModel) paymentReq;
		
		if (payPalReq.isEmpty())
			throw new IllegalArgumentException("There need to be at least one field for the type of payment");
		
		
		if (payPalReq.getNumber() != null)
			((PayPal) payment).setNumber(payPalReq.getNumber());
		
		if (payPalReq.getEmail() != null)
			((PayPal) payment).setEmail(payPalReq.getEmail());
	}
}
