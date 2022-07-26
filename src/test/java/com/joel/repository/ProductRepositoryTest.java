package com.joel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.joel.entity.CurrencyCode;
import com.joel.entity.Product;

/**
 * @author joel.rubio
 *
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	
	
	@DisplayName("Injected component repository is not null")
	@Test
	public void componentRepositoryNotNull() {
		
		assertThat(productRepository).isNotNull();
	}
	
	@DisplayName("Find all products from database")
	@Test
	public void findAllFromDatabase() {
		
		//given
		Product product1 = new Product("Laptop 1", "Laptop Dell 2021", BigDecimal.valueOf(1000), CurrencyCode.USD);
		Product product2 = new Product("Laptop 2", "Laptop Dell 2022", BigDecimal.valueOf(1000), CurrencyCode.USD);
		
		//execute
		productRepository.saveAll(List.of(product1, product2));
		
		//then
		assertThat(productRepository.findAll().isEmpty()).isFalse();
	}
	
	@DisplayName("Find product by id from database")
	@Test
	public void findByIdFromDatabase() {
		
		//given
		Product product = new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(1000), CurrencyCode.USD);
		
		//execute
		productRepository.save(product);
		
		//then
		assertThat(productRepository.findById(product.getId()).isPresent()).isTrue();
	}
	
	@DisplayName("Verify that a product with false id doesn't exist")
	@Test
	public void verifyProductNotExist() {
		
		//assert
		assertThat(productRepository.findById(1).isEmpty()).isTrue();
	}
	
	@DisplayName("Add new product to the database")
	@Test
	public void addNewProductToDatabase() {
		
		//given
		Product expectedProduct = new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(1000), CurrencyCode.USD);
		Product actualProduct;
		
		//execute
		actualProduct = productRepository.save(expectedProduct);
		
		//then
		assertThat(actualProduct).isEqualTo(expectedProduct);
	}
	
	@DisplayName("Delete product from database")
	@Test
	public void deleteFromDatabase() {
		
		//given
		Product product = new Product("Laptop 1", "Laptop Dell 2021", BigDecimal.valueOf(1000), CurrencyCode.USD);
		
		//execute
		product = productRepository.save(product);
		
		productRepository.delete(product);
		
		//then
		assertThat(productRepository.findById(product.getId()).isEmpty()).isTrue();
	}
}
