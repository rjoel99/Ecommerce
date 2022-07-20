package com.joel.service;

import java.util.Collection;

import com.joel.entity.Address;
import com.joel.model.AddressPatchRequestModel;
import com.joel.model.AddressRequestModel;

public interface AddressService {

	Collection<Address> findAll(int customerId, Integer page, Integer size, String[] sort);
	Address findById(int id);
	void add(int customerId, AddressRequestModel addressRequestModel);
	void updateSomeById(int addressId, AddressPatchRequestModel addressRequestModel);
	void updateAllById(int addressId, AddressRequestModel addressRequestModel);
	void deleteById(int id);
}
