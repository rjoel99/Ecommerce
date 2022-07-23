package com.joel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joel.entity.CurrencyCode;
import com.joel.entity.Product;
import com.joel.model.ProductRequestModel;
import com.joel.repository.ProductRepository;

/**
 * 
 * @author joel.rubio
 *
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	@Spy
	private ProductServiceImpl productService;
	
	
	@DisplayName("Find all products")
	@Test
	public void findAllWithoutSort() {
		
		//given
		List<Product> products = List.of(new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN));
		
		//when
		Mockito.when(productRepository.findAll()).thenReturn(products);
		
		//execute
		productService.findAll();
		
		//then
		Mockito.verify(productRepository, Mockito.times(1)).findAll();
	}
	
	@DisplayName("Find product by id")
	@Test
	public void findById() {
		
		//given
		int productId = 1;
		Optional<Product> expectedProduct = Optional.of(new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN));
		Product actualProduct;
		
		//when
		Mockito.when(productRepository.findById(productId)).thenReturn(expectedProduct);
		
		//execute
		actualProduct = productService.findById(productId);
		
		//then
		assertEquals(expectedProduct.get(), actualProduct);
	}
	
	@DisplayName("Throw an exception if the id doesn't exist")
	@Test
	public void throwExceptionWithInvalidId() {
		
		//given
		int productId = 0;
		
		//when
		Mockito.when(productRepository.findById(productId)).thenThrow(new EntityNotFoundException("The id doesn't exist"));
		
		//then
		assertThrows(EntityNotFoundException.class, () -> productService.findById(productId));
	}
	
	@DisplayName("Add new product")
	@Test
	public void addNewProduct() {
		
		//given
		ProductRequestModel productReq = new ProductRequestModel("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);
		Product product = new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);
		
		//when
		Mockito.when(productRepository.save(product)).thenReturn(product);
		
		//execute
		productService.add(productReq);
		
		//then
		Mockito.verify(productRepository, Mockito.times(1)).save(product);
	}
	
	@DisplayName("Update product by id")
	@Test
	public void updateById() {
		
		//given
		int productId = 1;
		ProductRequestModel productReq = new ProductRequestModel("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);
		Product product = new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);
		
		//when
		Mockito.doReturn(product).when(productService).findById(productId);
		Mockito.when(productRepository.save(product)).thenReturn(product);
		
		//execute
		productService.updateById(productId, productReq);
		
		//then
		Mockito.verify(productService, Mockito.times(1)).findById(productId);
		Mockito.verify(productRepository, Mockito.times(1)).save(product);
	}
	
	@DisplayName("Delete product by id")
	@Test
	public void deleteById() {
		
		//given
		int productId = 1;
		Product product = Mockito.mock(Product.class);
		
		//when
		Mockito.doReturn(product).when(productService).findById(productId);
		Mockito.doNothing().when(productRepository).delete(product);
		
		//execute
		productService.deleteById(productId);
		
		//then
		Mockito.verify(productService, Mockito.times(1)).findById(productId);
		Mockito.verify(productRepository, Mockito.times(1)).delete(product);
	}
}
