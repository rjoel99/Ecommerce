package com.joel.service;

import java.util.Collection;

import com.joel.entity.Customer;
import com.joel.model.CustomerRequestModel;

public interface CustomerService {

	Collection<Customer> findAll(Integer page, Integer sizePage, String[] sort);
	Customer findById(int id);
	void add(CustomerRequestModel customerRequestModel);
	void updateById(int id, CustomerRequestModel customerRequestModel);
	void deleteById(int id);
}
