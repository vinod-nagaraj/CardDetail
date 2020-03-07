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

import com.bank.creditcard.dto.RegistrationRequestDto;
import com.bank.creditcard.dto.RegistrationResponseDto;
import com.bank.creditcard.exception.DateInvalidException;
import com.bank.creditcard.exception.InsufficientBalanceException;
import com.bank.creditcard.exception.UnderAgeException;
import com.bank.creditcard.service.CardService;
import com.bank.creditcard.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class CardControllerTest {
	
	@InjectMocks
	CardController cardController;
	
	@Mock
	RegistrationService registrationService;
	
	@Mock
	CardService cardService;
	
	static RegistrationResponseDto registrationResponseDto =new RegistrationResponseDto();
	static RegistrationRequestDto registrationRequestDto=new RegistrationRequestDto();
	
	@Before
	public void setUp() {
		registrationRequestDto.setCustomerEmail("test");
		registrationResponseDto.setPassword("test");
	}
	
	@Test
	public void testRegisterPositive() throws UnderAgeException {
		log.info("Entering into testRegisterPositive");
		Mockito.when(registrationService.register(registrationRequestDto)).thenReturn(registrationResponseDto);
		Integer result = cardController.register(registrationRequestDto).getStatusCodeValue();
		assertEquals(200, result);
	}
	
	@Test
	public void testRegisterNegative() throws UnderAgeException {
		log.info("Entering into testRegisterNegative");
		Mockito.when(registrationService.register(registrationRequestDto)).thenReturn(registrationResponseDto);
		ResponseEntity<RegistrationResponseDto> registrationResponseDto = cardController.register(registrationRequestDto);
		Assert.assertNotNull(registrationResponseDto);
	}
	
	@Test
	public void testCardDetailsPositive() throws DateInvalidException, InsufficientBalanceException {
		log.info("Entering into testCardDetailsPositive");
		Mockito.when(cardService.getCardDetails(1L, 123, 123.4)).thenReturn(true);
		Boolean result=cardController.cardDetails(1L, 123, 123.4);
		assertEquals(true, result);
	}
	
	@Test
	public void testCardDetailsNegative() throws DateInvalidException, InsufficientBalanceException {
		log.info("Entering into testCardDetailsNegative");
		Mockito.when(cardService.getCardDetails(1L, 123, 123.4)).thenReturn(false);
		Boolean result=cardController.cardDetails(1L, 123, 123.4);
		assertEquals(false, result);
	}

}
