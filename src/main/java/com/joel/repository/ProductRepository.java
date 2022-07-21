package com.joel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.entity.Product;

/**
 * O
 * @author joel.rubio
 *
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
