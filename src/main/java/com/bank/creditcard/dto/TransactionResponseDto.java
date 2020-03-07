package com.bank.creditcard.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseDto {

	private String message;
	private Integer statusCode;
	private List<TransactionSummary> transactionList;
}
