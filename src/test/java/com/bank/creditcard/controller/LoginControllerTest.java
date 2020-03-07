package com.bank.creditcard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.bank.creditcard.dto.LoginRequestDto;
import com.bank.creditcard.dto.LoginResponseDto;
import com.bank.creditcard.exception.UserException;
import com.bank.creditcard.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginControllerTest {
	
	@InjectMocks
	LoginController loginController;
	
	@Mock
	LoginService loginService;
	
	static LoginResponseDto loginResponseDto = new LoginResponseDto();
	static LoginRequestDto loginRequestDto = new LoginRequestDto();

	@Before
	public void setUp() {
		loginRequestDto.setCustomerEmail("hema");
		loginRequestDto.setPassword("hema");
		loginResponseDto.setCreditCardId(1234L);
	}

	@Test
	public void testLoginPositive() throws UserException {
		log.info("Login Positive");
		Mockito.when(loginService.login(loginRequestDto)).thenReturn(loginResponseDto);
		Integer result = loginController.login(loginRequestDto).getStatusCodeValue();
		assertEquals(200, result);
	}

	@Test
	public void testCustomerLoginNegative() throws UserException {
		log.info("Login Negative");
		Mockito.when(loginService.login(loginRequestDto)).thenReturn(null);
		ResponseEntity<LoginResponseDto> loginResponse = loginController.login(loginRequestDto);
		Assert.assertNotNull(loginResponse);
	}

}
