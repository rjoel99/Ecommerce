package com.joel.service;

import java.util.Collection;

import com.joel.entity.Product;
import com.joel.model.ProductRequestModel;
import com.joel.model.ProductResponseModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface ProductService {

	Collection<ProductResponseModel> findAll();
	ProductResponseModel findByIdAsModel(int productId);
	Product findById(int productId);
	void add(ProductRequestModel productReq);
	void updateById(int productId, ProductRequestModel productReq);
	void deleteById(int productId);
}
