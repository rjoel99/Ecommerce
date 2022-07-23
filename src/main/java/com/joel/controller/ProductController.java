package com.joel.controller;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.message.SuccessMessage;
import com.joel.model.ProductRequestModel;
import com.joel.model.ProductResponseModel;
import com.joel.service.ProductService;

/**
 * 
 * @author joel.rubio
 *
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ProductResponseModel>> getAll() {
		
		Collection<ProductResponseModel> products = productService.findAll();
		
		return ResponseEntity.ok(products);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseModel> getById(@PathVariable int id) {
		
		ProductResponseModel product = productService.findByIdAsModel(id);
		
		return ResponseEntity.ok(product);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> add(@Valid @RequestBody ProductRequestModel productReq) {
		
		productService.add(productReq);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Product added")
				.datetime(LocalDateTime.now())
				.build());
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateById(@PathVariable int id, @Valid @RequestBody ProductRequestModel productReq) {
		
		productService.updateById(id, productReq);

		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Product updated")
				.datetime(LocalDateTime.now())
				.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable int id) {
		
		productService.deleteById(id);
		
		return ResponseEntity.ok(SuccessMessage.builder()
				.status(HttpStatus.OK.value())
				.message("Product deleted")
				.datetime(LocalDateTime.now())
				.build());
	}
}
