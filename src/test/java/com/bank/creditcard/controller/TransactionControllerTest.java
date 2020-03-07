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

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.dto.TransactionResponseDto;
import com.bank.creditcard.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionControllerTest {
	
	@InjectMocks
	TransactionController transactionController;
	
	@Mock
	TransactionService transactionService;
	
	static TransactionResponseDto transactionResponseDto =new TransactionResponseDto();
	
	@Before
	public void setUp() {
		transactionResponseDto.setMessage(ApplicationConstants.SUCCESS_MSG);
	}
	
	@Test
	public void testGetMonthlyStatementPositive() {
		log.info("Entering into testGetMonthlyStatementPositive");
		Mockito.when(transactionService.getMonthlyStatement(12, 2019, 1L)).thenReturn(transactionResponseDto);
		Integer result = transactionController.getMonthlyStatement(12, 2019, 1L).getStatusCodeValue();
		assertEquals(200, result);
	}
	
	@Test
	public void testGetMonthlyStatementNegative() {
		log.info("Entering into testGetMonthlyStatementNegative");
		Mockito.when(transactionService.getMonthlyStatement(12, 2019, 1L)).thenReturn(transactionResponseDto);
		ResponseEntity<TransactionResponseDto> transactionResponseDto = transactionController.getMonthlyStatement(11, 2019, 1L);
		Assert.assertNotNull(transactionResponseDto);
	}
	
	@Test
	public void testAddTransactionPositive() {
		log.info("Entering into testAddTransactionPositive");
		Mockito.when(transactionService.addTransaction(1L, 12.3)).thenReturn(true);
		Boolean result=transactionController.addTransaction(1L, 12.3);
		assertEquals(true, result);
	}
	
	@Test
	public void testAddTransactionNegative() {
		log.info("Entering into testAddTransactionNegative");
		Mockito.when(transactionService.addTransaction(2L, 12.3)).thenReturn(false);
		Boolean result=transactionController.addTransaction(2L, 12.3);
		assertEquals(false, result);
	}

}
