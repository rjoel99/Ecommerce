package com.joel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
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

import com.joel.entity.Cart;
import com.joel.entity.CurrencyCode;
import com.joel.entity.Customer;
import com.joel.entity.Product;
import com.joel.repository.CartRepository;
import com.joel.service.ProductService;

/**
 * 
 * @author joel.rubio
 *
 */
@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

	@Mock
	private CartRepository cartRepository;
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	@Spy
	private CartServiceImpl cartService;
	
	
	@DisplayName("Find cart by id")
	@Test
	public void findById() {
		
		//given
		int cartId = 1;
		Customer customer = new Customer("John", "Wick", "435456765");
		Optional<Cart> expectedCart = Optional.of(new Cart(customer));
		Cart actualCart;
		
		//when
		Mockito.when(cartRepository.findById(cartId)).thenReturn(expectedCart);
		
		//execute
		actualCart = cartService.findById(cartId);
		
		//then
		assertEquals(expectedCart.get(), actualCart);
	}
	
	@DisplayName("Throw an exception if the id doesn't exist")
	@Test
	public void throwExceptionWithInvalidId() {
		
		//given
		int cartId = 0;
		
		//when
		Mockito.when(cartRepository.findById(cartId)).thenThrow(new EntityNotFoundException("The id doesn't exist"));
		
		//then
		assertThrows(EntityNotFoundException.class, () -> cartService.findById(cartId));
	}
	
	@DisplayName("Create new cart")
	@Test
	public void createNewCart() {
		
		//given
		Customer customer = new Customer("John", "Wick", "435456765");
		Cart cart = new Cart(customer);
		
		//when
		Mockito.when(cartRepository.save(cart)).thenReturn(cart);
		
		//execute
		cartService.create(customer);
		
		//then
		Mockito.verify(cartRepository, Mockito.times(1)).save(cart);		
	}
	
	@DisplayName("Add product to cart by id")
	@Test
	public void addProductToCartById() {
		
		//given
		int cartId = 1;
		int productId = 1;
		Customer customer = new Customer("John", "Wick", "435456765");
		Cart cart = new Cart(customer);
		Product product = new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);
		Cart cartMock = Mockito.mock(Cart.class);
		
		//when
		Mockito.doReturn(cartMock).when(cartService).findById(cartId);
		Mockito.when(productService.findById(productId)).thenReturn(product);
		Mockito.when(cartRepository.save(cartMock)).thenReturn(cart);
		
		//execute
		cartService.addProductToCart(cartId, productId);
		
		//then
		Mockito.verify(cartService, Mockito.times(1)).findById(cartId);
		Mockito.verify(productService, Mockito.times(1)).findById(productId);
		Mockito.verify(cartRepository, Mockito.times(1)).save(cartMock);
	}
	
	@DisplayName("Remove product from cart by id")
	@Test
	public void removeProductFromCartById() {
		
		//given
		int cartId = 1;
		int productId = 1;
		Customer customer = new Customer("John", "Wick", "435456765");
		Cart cart = new Cart(customer);
		Product product = new Product("Laptop", "Laptop Dell 2020", BigDecimal.valueOf(20000), CurrencyCode.MXN);
		Cart cartMock = Mockito.mock(Cart.class);
		
		//when
		Mockito.doReturn(cartMock).when(cartService).findById(cartId);
		Mockito.when(productService.findById(productId)).thenReturn(product);
		Mockito.when(cartRepository.save(cartMock)).thenReturn(cart);
		
		//execute
		cartService.removeProductFromCart(cartId, productId);
		
		//then
		Mockito.verify(cartService, Mockito.times(1)).findById(cartId);
		Mockito.verify(productService, Mockito.times(1)).findById(productId);
		Mockito.verify(cartRepository, Mockito.times(1)).save(cartMock);
	}
}
