package com.bank.creditcard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.creditcard.entity.Card;
import com.bank.creditcard.exception.DateInvalidException;
import com.bank.creditcard.exception.InsufficientBalanceException;
import com.bank.creditcard.repository.CardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class CardServiceTest {

	@InjectMocks
	CardServiceImpl cardServiceImpl;

	@Mock
	CardRepository cardRepository;

	Card card = new Card();

	@Before
	public void setUp() {
		card.setCardNumber(1L);
		card.setCvv(123);
		card.setValidFrom(LocalDate.of(2019, 12, 01));
		card.setValidTo(LocalDate.of(2020, 01, 12));
		card.setCardBalance(100.0);
	}

	@Test
	public void testGetCardDetailsPositive() throws DateInvalidException, InsufficientBalanceException {
		log.info("Entering into testGetCardDetailsPositive");
		Mockito.when(cardRepository.findByCardNumberAndCvv(1L, 123)).thenReturn(Optional.of(card));
		Boolean result = cardServiceImpl.getCardDetails(1L, 123, 10.0);
		assertEquals(true, result);
	}

	@Test
	public void testGetCardDetailsNegative() throws DateInvalidException, InsufficientBalanceException {
		log.info("Entering into testGetCardDetailsNegative");
		Mockito.when(cardRepository.findByCardNumberAndCvv(1L, 123)).thenReturn(Optional.of(card));
		Boolean result = cardServiceImpl.getCardDetails(2L, 123, 10.0);
		assertEquals(false, result);
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testGetCardDetailsInsufficientBalance() throws DateInvalidException, InsufficientBalanceException {
		log.info("Entering into testGetCardDetailsInsufficientBalance");
		Mockito.when(cardRepository.findByCardNumberAndCvv(1L, 123)).thenReturn(Optional.of(card));
		cardServiceImpl.getCardDetails(1L, 123, 1001.0);
	}

	@Test(expected = DateInvalidException.class)
	public void testGetCardDetailsInvalidDate2() throws DateInvalidException, InsufficientBalanceException {
		log.info("Entering into testGetCardDetailsInvalidDate");
		card.setValidTo(LocalDate.of(2012, 8, 12));
		Mockito.when(cardRepository.findByCardNumberAndCvv(1L, 123)).thenReturn(Optional.of(card));
		cardServiceImpl.getCardDetails(1L, 123, 10.0);
	}

}
