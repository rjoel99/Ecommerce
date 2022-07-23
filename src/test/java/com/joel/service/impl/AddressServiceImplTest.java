package com.joel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.joel.entity.Address;
import com.joel.model.AddressRequestModel;
import com.joel.repository.AddressRepository;
import com.joel.service.CustomerService;

/**
 * 
 * @author joel.rubio
 *
 */
@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

	
	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	@Spy
	private AddressServiceImpl addressService;
	
	
	@DisplayName("Find all addresses")
	@Test
	public void findAllWithoutSort() {
		
		//given
		int customerId = 1;
		int page = 0;
		int size = 5;
		Pageable pageable = PageRequest.of(page, size);
		
		@SuppressWarnings("unchecked")
		Page<Address> addresses = Mockito.mock(Page.class);
		
		//when
		Mockito.when(addressRepository.findAllByCustomerId(customerId, pageable)).thenReturn(addresses);
		
		//execute
		addressService.findAll(customerId, page, size, null);
		
		//then
		Mockito.verify(addressRepository, Mockito.times(1)).findAllByCustomerId(customerId, pageable);
	}
	
	@DisplayName("Find address by id")
	@Test
	public void findById() {
		
		//given
		int addressId = 1;
		Optional<Address> expectedAddress = Optional.of(new Address("Some street", "Some city", "Some state", "23432"));
		Address actualAddress;
		
		//when
		Mockito.when(addressRepository.findById(addressId)).thenReturn(expectedAddress);
		
		//execute
		actualAddress = addressService.findById(addressId);
		
		//then
		assertEquals(expectedAddress.get(), actualAddress);
	}
	
	@DisplayName("Throw an exception if the id doesn't exist")
	@Test
	public void throwExceptionWithInvalidId() {
		
		//given
		int addressId = 0;
		
		//when
		Mockito.when(addressRepository.findById(addressId)).thenThrow(new EntityNotFoundException("The id doesn't exist"));
		
		//then
		assertThrows(EntityNotFoundException.class, () -> addressService.findById(addressId));
	}
	
	@DisplayName("Add new address")
	@Test
	public void addNewAddress() {
		
		//given
		int customerId = 1;
		AddressRequestModel addressReq = new AddressRequestModel("Some street", "Some city", "Some state", "23432");
		Address address = new Address("Some street", "Some city", "Some state", "23432");
		
		//when
		Mockito.when(addressRepository.save(address)).thenReturn(address);
		
		//execute
		addressService.add(customerId, addressReq);
		
		//then
		Mockito.verify(addressRepository, Mockito.times(1)).save(address);
	}
	
	@DisplayName("Update address by id")
	@Test
	public void updateById() {
		
		//given
		int addressId = 1;
		AddressRequestModel addressReq = new AddressRequestModel("Some street", "Some city", "Some state", "23432");
		Address address = new Address("Some street", "Some city", "Some state", "23432");
		
		//when
		Mockito.doReturn(address).when(addressService).findById(addressId);
		Mockito.when(addressRepository.save(address)).thenReturn(address);
		
		//execute
		addressService.updateAllById(addressId, addressReq);
		
		//then
		Mockito.verify(addressService, Mockito.times(1)).findById(addressId);
		Mockito.verify(addressRepository, Mockito.times(1)).save(address);
	}
	
	@DisplayName("Delete address by id")
	@Test
	public void deleteById() {
		
		//given
		int addressId = 1;
		Address address = new Address("Some street", "Some city", "Some state", "23432");
		
		//when
		Mockito.doReturn(address).when(addressService).findById(addressId);
		Mockito.doNothing().when(addressRepository).delete(address);
		
		//execute
		addressService.deleteById(addressId);
		
		//then
		Mockito.verify(addressService, Mockito.times(1)).findById(addressId);
		Mockito.verify(addressRepository, Mockito.times(1)).delete(address);
	}
}
