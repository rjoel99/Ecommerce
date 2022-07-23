package com.joel.service;

import java.util.Collection;

import com.joel.entity.Address;
import com.joel.model.AddressPatchRequestModel;
import com.joel.model.AddressRequestModel;
import com.joel.model.AddressResponseModel;

/**
 * 
 * @author joel.rubio
 *
 */
public interface AddressService {

	Collection<AddressResponseModel> findAll(int customerId, Integer page, Integer size, String[] sort);
	AddressResponseModel findByIdAsModel(int addressId);
	Address findById(int addressId);
	void add(int customerId, AddressRequestModel addressRequestModel);
	void updateSomeById(int addressId, AddressPatchRequestModel addressRequestModel);
	void updateAllById(int addressId, AddressRequestModel addressRequestModel);
	void deleteById(int addressId);
}
