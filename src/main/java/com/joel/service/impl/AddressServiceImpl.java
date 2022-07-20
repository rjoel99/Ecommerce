package com.joel.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.entity.Address;
import com.joel.entity.Customer;
import com.joel.model.AddressPatchRequestModel;
import com.joel.model.AddressRequestModel;
import com.joel.repository.AddressRepository;
import com.joel.service.AddressService;
import com.joel.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author joel.rubio
 *
 */
@Slf4j
@Transactional
@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;
	private CustomerService customerService;
	
	public AddressServiceImpl(AddressRepository addressRepository, CustomerService customerService) {
		this.addressRepository = addressRepository;
		this.customerService = customerService;
	}
	
	public Collection<Address> findAll(int customerId, Integer page, Integer size, String[] sort) {
		
		log.info("Getting all addresses...");
		
		Collection<Address> addresses = null;
		
		if (sort != null)
			addresses = addressRepository.findAllByCustomerId(customerId, PageRequest.of(page, size, Sort.by(getSortProperties(sort)))).toList();
		else
			addresses = addressRepository.findAllByCustomerId(customerId, PageRequest.of(page, size)).toList();
		
		log.info("Addresses obtained");
		
		return addresses;
	}
	
	private List<Order> getSortProperties(String[] sort) {
		
		List<Order> sortProperties = new ArrayList<>();
		
		if ("asc".equals(sort[1].toLowerCase())) {
			
			sortProperties.add(Order.asc(sort[0]));

		} else if ("desc".equals(sort[1].toLowerCase())) {
			
			sortProperties.add(Order.desc(sort[0]));
			
		} else {
			
			for (String parameter : sort) {
				
				String[] properties = parameter.trim().split(",");
				
				if (properties.length == 2 && "desc".equals(properties[1].toLowerCase()))
					sortProperties.add(Order.desc(properties[0]));
				else
					sortProperties.add(Order.asc(properties[0]));
			}
		}
		
		return sortProperties;
	}

	public Address findById(int addressId) {
		
		log.info("Getting address by id {}...", addressId);
		
		Address address = addressRepository.findById(1)
				.orElseThrow(() -> new EntityNotFoundException("The address with id " + addressId + " doesn't exist"));
		
		log.info("Address with id {} obtained", addressId);
		
		return address;
	}
	
	public void add(int customerId, AddressRequestModel addressReqModel) {
		
		Customer customer = customerService.findById(customerId);
		
		log.info("Adding new address...");
		
		Address address = new Address(addressReqModel.getAddressLine(), addressReqModel.getCity(), 
									  addressReqModel.getState(), addressReqModel.getZipCode(), customer);
		
		addressRepository.save(address);
		
		log.info("Address added");
	}
	
	public void updateSomeById(int addressId, AddressPatchRequestModel addressReqModel) {
		
		Address addressSaved = findById(addressId);
		
		log.info("Updating some attributes of address with id {}...", addressId);
		
		if (addressReqModel.getAddressLine() != null)
			addressSaved.setAddressLine(addressReqModel.getAddressLine());
		
		if (addressReqModel.getCity() != null)
			addressSaved.setCity(addressReqModel.getCity());
		
		if (addressReqModel.getState() != null)
			addressSaved.setState(addressReqModel.getState());
		
		if (addressReqModel.getZipCode() != null)
			addressSaved.setZipCode(addressReqModel.getZipCode());
		
		addressRepository.save(addressSaved);
		
		log.info("Address with id {} updated", addressId);
	}
	
	public void updateAllById(int addressId, AddressRequestModel addressReqModel) {
		
		Address addressSaved = findById(addressId);
		
		log.info("Updating all attributes of address with id {}...", addressId);
		
		addressSaved.setAddressLine(addressReqModel.getAddressLine());
		addressSaved.setCity(addressReqModel.getCity());
		addressSaved.setState(addressReqModel.getState());
		addressSaved.setZipCode(addressReqModel.getZipCode());
		
		addressRepository.save(addressSaved);
		
		log.info("Address with id {} updated", addressId);
	}
	
	public void deleteById(int addressId) {
		
		Address address = findById(addressId);
		
		log.info("Deleting address with id {}...", addressId);
		
		addressRepository.delete(address);
		
		log.info("Address with id {} deleted", addressId);
	}
}
