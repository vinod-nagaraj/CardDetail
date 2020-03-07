package com.bank.creditcard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.creditcard.dto.TransactionResponseDto;
import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Transaction;
import com.bank.creditcard.exception.CardException;
import com.bank.creditcard.exception.NoTransactionException;
import com.bank.creditcard.repository.CardRepository;
import com.bank.creditcard.repository.TransactionRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceImplTest {
	
	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;
	
	@Mock
	CardRepository cardRepository;
	
	@Mock
	TransactionRepository transactionRepository;
	
	Card card= new Card();
	Card card1= new Card();
	Transaction transaction= new Transaction();
	List<Transaction> transactionList= new ArrayList<>();
	
	@Before
	public void setUp() {
		card.setCardNumber(1L);
		card1.setCardNumber(2L);
		
		transaction.setCardNumber(card);
		transaction.setTransactionTime(LocalDateTime.now());
		transactionList.add(transaction);
	}
	

	@Test(expected=CardException.class)
	public void testGetMonthlyStatement() {
		Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(card));
		transactionServiceImpl.getMonthlyStatement(1, 2, 1L);
	}
	
	@Test(expected=NoTransactionException.class)
	public void testGetMonthlyStatementTransactionNegative() {
		Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
		Mockito.when(transactionRepository.findByCardNumber(card1)).thenReturn(Optional.of(transactionList));
		transactionServiceImpl.getMonthlyStatement(1, 2, 1L);
	}
	
	@Test
	public void testGetMonthlyStatementTransaction() {
		Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
		Mockito.when(transactionRepository.findByCardNumber(card)).thenReturn(Optional.of(transactionList));
		TransactionResponseDto transactionResponseDto=transactionServiceImpl.getMonthlyStatement(12, 2019, 1L);
		Assert.assertNotNull(transactionResponseDto);
	}
}
