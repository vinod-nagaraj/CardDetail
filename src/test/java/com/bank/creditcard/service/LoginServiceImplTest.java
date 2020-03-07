package com.bank.creditcard.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.creditcard.dto.LoginRequestDto;
import com.bank.creditcard.dto.LoginResponseDto;
import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Customer;
import com.bank.creditcard.exception.UserException;
import com.bank.creditcard.repository.CardRepository;
import com.bank.creditcard.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginServiceImplTest {

	@InjectMocks
	LoginServiceImpl loginServiceImplementation;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	CardRepository cardRepository;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponsedto = new LoginResponseDto();
	Customer customer = new Customer();
	Customer customer1 = new Customer();
	Card card = new Card();

	@Before
	public void setUp() {
		loginRequestDto.setCustomerEmail("c@gmail.com");
		loginRequestDto.setPassword("c");
		loginResponsedto.setCustomerID(1L);

		customer.setCustomerId(1L);
		customer.setFirstName("c");
		customer.setLastName("m");

		customer1.setCustomerId(2L);
		customer1.setFirstName("c");
		customer1.setLastName("m");

		card.setCustomerId(customer);
		card.setCardNumber(1L);

	}

	@Test(expected = UserException.class)
	public void testUserLoginNegative() throws UserException {
		Mockito.when(customerRepository.findByCustomerEmailAndPassword("a", loginRequestDto.getPassword()))
				.thenReturn(Optional.of(customer));
		loginServiceImplementation.login(loginRequestDto);
	}

	@Test
	public void testUserLogin() throws UserException {
		Mockito.when(customerRepository.findByCustomerEmailAndPassword(loginRequestDto.getCustomerEmail(),
				loginRequestDto.getPassword())).thenReturn(Optional.of(customer));
		Mockito.when(cardRepository.findByCustomerId(customer)).thenReturn(Optional.of(card));
		LoginResponseDto loginResponseDto = loginServiceImplementation.login(loginRequestDto);
		Assert.assertNotNull(loginResponseDto);
	}

	@Test
	public void testUserLoginTypeNegative() throws UserException {
		Mockito.when(customerRepository.findByCustomerEmailAndPassword(loginRequestDto.getCustomerEmail(),
				loginRequestDto.getPassword())).thenReturn(Optional.of(customer));
		Mockito.when(cardRepository.findByCustomerId(customer1)).thenReturn(Optional.of(card));
		LoginResponseDto loginResponseDto = loginServiceImplementation.login(loginRequestDto);
		Assert.assertNotNull(loginResponseDto);
	}
}
