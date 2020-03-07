package com.bank.creditcard.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.dto.TransactionResponseDto;
import com.bank.creditcard.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Muthu 
 * This class is contains the operations related to credit card
 *         transactions.Used for adding transaction and getting transaction
 *         history
 * 
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {
	@Autowired
	TransactionService transactionService;

	/**
	 * 
	 * @author Muthu
	 * 
	 *         Method is used to add transaction based on card number
	 * 
	 * @param cardNumber
	 * @param price
	 * @return true/false
	 */
	@PostMapping
	public Boolean addTransaction(@RequestParam Long cardNumber, @RequestParam Double price) {
		log.info("Adding transaction");
		return transactionService.addTransaction(cardNumber, price);
	}

	/**
	 * This method is used to get the monthly transaction summary statement
	 * 
	 * @author Chethana
	 * @param month of data type Integer
	 * @param year of data type Integer
	 * @param creditCardId of data type Long
	 * @return list of Transaction details for a particular month in a given year
	 */
	@GetMapping
	public ResponseEntity<TransactionResponseDto> getMonthlyStatement(@RequestParam int month, @RequestParam int year,
			@RequestParam Long creditCardId) {
		log.info("Entering into getMonthlyStatement() of TransactionController");
		TransactionResponseDto transactionResponseDto = transactionService.getMonthlyStatement(month, year,
				creditCardId);
		if (Objects.isNull(transactionResponseDto)) {
			TransactionResponseDto transactionResponse = new TransactionResponseDto();
			transactionResponse.setMessage(ApplicationConstants.FAILURE_MSG);
			transactionResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(transactionResponse, HttpStatus.NOT_FOUND);
		}
		transactionResponseDto.setMessage(ApplicationConstants.SUCCESS_MSG);
		transactionResponseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(transactionResponseDto, HttpStatus.OK);
	}
}
