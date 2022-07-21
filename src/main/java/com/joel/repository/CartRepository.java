package com.joel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.entity.Cart;

/**
 * 
 * @author joel.rubio
 *
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
