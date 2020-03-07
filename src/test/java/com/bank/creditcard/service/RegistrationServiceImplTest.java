package com.bank.creditcard.service;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.creditcard.dto.RegistrationRequestDto;
import com.bank.creditcard.dto.RegistrationResponseDto;
import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Customer;
import com.bank.creditcard.exception.UnderAgeException;
import com.bank.creditcard.repository.CardRepository;
import com.bank.creditcard.repository.CustomerRepository;


@RunWith(MockitoJUnitRunner.Silent.class)
public class RegistrationServiceImplTest {
	@InjectMocks
	RegistrationServiceImpl registrationService;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	CardRepository cardRepository;
	
	Customer customer= new Customer();
	Card card= new Card();
	RegistrationRequestDto registrationRequestDto= new RegistrationRequestDto();
	
	@Before
	public void setup() {
		customer.setCustomerEmail("a@gmail.com");
		customer.setCustomerId(1L);
		customer.setDateOfBirth(LocalDate.now());
		customer.setFirstName("c");
		customer.setGender("female");
		customer.setLastName("m");
		customer.setMobile(123L);
		customer.setPassword("abc");
		customer.setProfession("se");
		customer.setSalary(1000.00);
		
		
		card.setCardBalance(1000.00);
		card.setCardNumber(1L);
		card.setCardType("credit");
		card.setCustomerId(customer);
		card.setCvv(124);
		card.setValidFrom(LocalDate.now());
		card.setValidTo(LocalDate.now().plusDays(2));
		
		registrationRequestDto.setCardBalance(1000.00);
		registrationRequestDto.setCustomerEmail("a@gmail.com");
		registrationRequestDto.setDateOfBirth(LocalDate.now());
		registrationRequestDto.setFirstName("c");
		registrationRequestDto.setGender("female");
		registrationRequestDto.setLastName("m");
		registrationRequestDto.setMobile(123L);
		registrationRequestDto.setProfession("se");
		registrationRequestDto.setSalary(1000.00);
	}

	@Test(expected=UnderAgeException.class)
	public void testRegisterAgeValidationNegative() throws UnderAgeException {
		registrationService.register(registrationRequestDto);
	}
	
	@Test(expected=NullPointerException.class)
	public void testRegister() throws UnderAgeException {
		registrationRequestDto.setDateOfBirth(LocalDate.now().minusYears(25));
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		Mockito.when(cardRepository.save(card)).thenReturn(card);
		RegistrationResponseDto registrationResponseDto=registrationService.register(registrationRequestDto);
		Assert.assertNotNull(registrationResponseDto);
	}
}
