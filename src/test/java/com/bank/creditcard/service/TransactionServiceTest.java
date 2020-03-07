package com.bank.creditcard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Customer;
import com.bank.creditcard.entity.Transaction;
import com.bank.creditcard.repository.CardRepository;
import com.bank.creditcard.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceTest {
	
	@InjectMocks
	TransactionServiceImpl transactionServiceImpl; 

	@Mock
	CardRepository cardRepository;
	
	@Mock
	TransactionRepository transactionRepository;
	
	 Card card = new Card();
	 Customer customer= new Customer();
	 Transaction transaction = new Transaction();
	
	@Before
	public void setUp() {
		customer.setCustomerId(1L);
		
		card.setCardNumber(1L);
		card.setCardBalance(1234.5);
		card.setCardType("credit");
		card.setCustomerId(customer);
		card.setCvv(123);
		card.setValidFrom(LocalDate.now());
		card.setValidTo(LocalDate.now().plusDays(1));
		transaction.setCardNumber(card);
		transaction.setTransactionType(ApplicationConstants.DEBIT_MESSAGE);
	}

	@Test
	public void testAddTransactionNegative() {
		log.info("Entering into testAddTransactionNegative");
		Mockito.when(cardRepository.findByCardNumber(2L)).thenReturn(card);
		Boolean result=transactionServiceImpl.addTransaction(1L, 12.3);
		assertEquals(false, result);
	}
	
	@Test
	public void testAddTransactionPositive() {
		log.info("Entering into testAddTransactionPositive");
		Mockito.when(cardRepository.findByCardNumber(1L)).thenReturn(card);
		Boolean result=transactionServiceImpl.addTransaction(1L, 12.3);
		assertEquals(true, result);
	}
}
