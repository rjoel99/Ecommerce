package com.joel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.entity.Order;

/**
 * 
 * @author joel.rubio
 *
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findAllByCartId(int cartId);
}
