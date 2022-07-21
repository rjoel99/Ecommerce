package com.joel.service;

import java.util.Collection;

import com.joel.entity.Product;
import com.joel.model.ProductRequestModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface ProductService {

	Collection<Product> findAll();
	Product findById(int productId);
	void add(ProductRequestModel productReq);
	void updateById(int productId, ProductRequestModel productReq);
	void deleteById(int productId);
}
