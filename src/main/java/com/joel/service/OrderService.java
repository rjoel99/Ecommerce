package com.joel.service;

import java.util.Collection;

import com.joel.entity.Order;
import com.joel.model.OrderRequestModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface OrderService {

	Collection<Order> findAllByCartId(int cartId);
	Order findById(int orderId);
	Order create(int cartId);
	void updateById(int orderId, OrderRequestModel orderReq);
}
