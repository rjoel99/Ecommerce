package com.joel.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.joel.entity.Customer;
import com.joel.model.CustomerRequestModel;
import com.joel.repository.CustomerRepository;
import com.joel.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author joel.rubio
 *
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public Collection<Customer> findAll(Integer page, Integer sizePage, String[] sort) {
		
		log.info("Getting all customers...");
		
		Collection<Customer> customers = null;
		
		if (sort != null)
			customers = customerRepository.findAll(PageRequest.of(page, sizePage, Sort.by(getSortProperties(sort)))).toList();
		else
			customers = customerRepository.findAll(PageRequest.of(page, sizePage)).toList();
		
		log.info("Customers obtained");
		
		return customers;
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
	
	public Customer findById(int id) {
		
		log.info("Getting customer by id {}...", id);
		
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("The customer with id " + id + " doesn't exist"));
		
		log.info("Customer with id {} obtained", id);
		
		return customer;
	}
	
	public void add(CustomerRequestModel customerReqModel) {
		
		log.info("Adding new customer...");
		
		Customer customer = new Customer(customerReqModel.getFirstName(), customerReqModel.getLastName(), customerReqModel.getPhone());
		
		customerRepository.save(customer);
	
		log.info("Customer added");
	}
	
	public void updateById(int id, CustomerRequestModel customerToUpdate) {
		
		Customer customerSaved = findById(id);
		
		log.info("Updating customer with id {}...", id);
		
		customerSaved.setFirstName(customerToUpdate.getFirstName());
		customerSaved.setLastName(customerToUpdate.getLastName());
		customerSaved.setPhone(customerToUpdate.getPhone());
		
		customerRepository.save(customerSaved);
		
		log.info("Customer with id {} updated", id);
	}
	
	public void deleteById(int id) {
		
		Customer customer = findById(id);
		
		log.info("Deleting customer with id {}...", id);
		
		customerRepository.delete(customer);
		
		log.info("Customer with id {} deleted", id);
	}
}
