package com.joel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.entity.Address;

/**
 * 
 * @author joel.rubio
 *
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {

	Page<Address> findAllByCustomerId(int customerId, Pageable pageable);
}
