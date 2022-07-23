package com.joel.service;

import java.util.Collection;

import com.joel.entity.Customer;
import com.joel.model.CustomerRequestModel;
import com.joel.model.CustomerResponseModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface CustomerService {

	Collection<CustomerResponseModel> findAll(Integer page, Integer sizePage, String[] sort);
	CustomerResponseModel findByIdAsModel(int customerId);
	Customer findById(int customerId);
	void add(CustomerRequestModel customerRequestModel);
	void updateById(int customerId, CustomerRequestModel customerRequestModel);
	void deleteById(int customerId);
}
