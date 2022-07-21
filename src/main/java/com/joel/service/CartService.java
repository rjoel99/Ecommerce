package com.joel.service;

import com.joel.entity.Cart;
import com.joel.entity.Customer;

/**
 * 
 * @author joel.rubio
 *
 */
public interface CartService {

	Cart findById(int cartId);
	void create(Customer customer);
	void updateById(int cartId, int productId);
//	void deleteById(int cartId);
}
