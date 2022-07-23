package com.joel.service;

import com.joel.entity.Cart;
import com.joel.entity.Customer;
import com.joel.model.CartResponseModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface CartService {

	CartResponseModel findByIdAsModel(int cartId);
	Cart findById(int cartId);
	void create(Customer customer);
	void addProductToCart(int cartId, int productId);
	void removeProductFromCart(int cartId, int productId);
}
