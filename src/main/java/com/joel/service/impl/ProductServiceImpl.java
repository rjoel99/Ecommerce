package com.joel.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.joel.entity.Product;
import com.joel.model.ProductRequestModel;
import com.joel.model.ProductResponseModel;
import com.joel.repository.ProductRepository;
import com.joel.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author joel.rubio
 *
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Collection<ProductResponseModel> findAll() {
		
		log.info("Getting all products...");
		
		Collection<Product> products = productRepository.findAll();
		
		log.info("Products obtained");
		
		return products.stream()
				.map(p -> ProductResponseModel.builder()
						.name(p.getName())
						.description(p.getDescription())
						.price(p.getPrice())
						.currencyCode(p.getCurrencyCode())
						.build())
				.collect(Collectors.toList());
	}
	
	@Override
	public Product findById(int productId) {
		
		log.info("Getting product with id {}...", productId);
	
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("The product with id " + productId + " doesn't exist"));
		
		log.info("Product with id {} obtained", productId);
		
		return product;
	}
	
	@Override
	public ProductResponseModel findByIdAsModel(int productId) {
	
		Product product = findById(productId);
		
		return ProductResponseModel.builder()
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.currencyCode(product.getCurrencyCode())
				.build();
	}
	
	@Override
	public void add(ProductRequestModel productReq) {
		
		log.info("Adding new product...");
		
		Product product = new Product(productReq.getName(), productReq.getDescription(), productReq.getPrice(), productReq.getCurrencyCode());
		
		productRepository.save(product);
		
		log.info("Product added");
	}
	
	@Override
	public void updateById(int productId, ProductRequestModel productReq) {
	
		Product product = findById(productId);
		
		log.info("Updating product with id {}...", productId);
		
		product.setName(productReq.getName());
		product.setDescription(productReq.getDescription());
		product.setPrice(productReq.getPrice());
		product.setCurrencyCode(productReq.getCurrencyCode());
		
		productRepository.save(product);
		
		log.info("Product with id {} updated", productId);
	}
	
	@Override
	public void deleteById(int productId) {
	
		Product product = findById(productId);
		
		log.info("Deleting product with id {}...", productId);
		
		product.removeFromCart();
		
		productRepository.delete(product);
		
		log.info("Product with id {} deleted", productId);
	}
}
