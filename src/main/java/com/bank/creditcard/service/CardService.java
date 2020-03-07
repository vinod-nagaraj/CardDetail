package com.bank.creditcard.service;

import com.bank.creditcard.exception.DateInvalidException;
import com.bank.creditcard.exception.InsufficientBalanceException;

public interface CardService {

	Boolean getCardDetails(Long cardNumber, Integer cvv,Double price) throws DateInvalidException, InsufficientBalanceException;

}
