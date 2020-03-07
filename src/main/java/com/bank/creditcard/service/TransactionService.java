package com.bank.creditcard.service;


import com.bank.creditcard.dto.TransactionResponseDto;

public interface TransactionService {
	public TransactionResponseDto getMonthlyStatement(int month,int year,Long creditCardId);
	Boolean addTransaction(Long cardNumber, Double price);

}
