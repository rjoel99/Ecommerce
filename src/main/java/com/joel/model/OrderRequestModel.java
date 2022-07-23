package com.joel.model;

import javax.validation.constraints.NotNull;

import com.joel.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author joel.rubio
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class OrderRequestModel {

	@NotNull
	private OrderStatus status;
}
