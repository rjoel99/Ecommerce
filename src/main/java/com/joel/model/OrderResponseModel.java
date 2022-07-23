/**
 * 
 */
package com.joel.model;

import java.time.LocalDateTime;

import com.joel.entity.OrderStatus;

import lombok.Builder;
import lombok.Getter;

/**
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class OrderResponseModel {

	private int id;
	private OrderStatus status;
	private LocalDateTime createdOn;
}
