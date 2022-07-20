package com.joel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.entity.Customer;

/**
 * 
 * @author joel.rubio
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
