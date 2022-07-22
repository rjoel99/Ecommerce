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
	void addProductToCart(int cartId, int productId);
	void removeProductFromCart(int cartId, int productId);
}
