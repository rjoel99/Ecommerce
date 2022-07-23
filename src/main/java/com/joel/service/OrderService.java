package com.joel.service;

import java.util.Collection;

import com.joel.entity.Order;
import com.joel.model.OrderRequestModel;
import com.joel.model.OrderResponseModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface OrderService {

	Collection<OrderResponseModel> findAll(int cartId);
	OrderResponseModel findByIdAsModel(int orderId);
	Order findById(int orderId);
	Order create(int cartId);
	void updateById(int orderId, OrderRequestModel orderReq);
}
