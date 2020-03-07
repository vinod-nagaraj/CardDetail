package com.bank.creditcard.service;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.dto.TransactionResponseDto;
import com.bank.creditcard.dto.TransactionSummary;
import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Transaction;
import com.bank.creditcard.exception.CardException;
import com.bank.creditcard.exception.NoTransactionException;
import com.bank.creditcard.repository.CardRepository;
import com.bank.creditcard.repository.TransactionRepository;
/*
 * Used for adding transaction and getting transaction history
 * 
 */

import lombok.extern.slf4j.Slf4j;
/**
 * @author Muthu 
 * This class is contains the operations related to credit card
 *         transactions.Used for adding transaction and getting transaction
 *         history
 * 
 */
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	TransactionRepository transactionRepository;
	
	/**
	 * @author Muthu
	 * 
	 *         Method is used to add transaction based on card number
	 * 
	 * @param cardNumber
	 * @param price
	 * @return
	 */
	@Override
	public Boolean addTransaction(Long cardNumber, Double price) {
		Card card = cardRepository.findByCardNumber(cardNumber);
		Transaction transaction = new Transaction();
		if (!(Objects.isNull(card))) {
			Double priceUpdate = card.getCardBalance() - price;
			card.setCardBalance(priceUpdate);
			cardRepository.save(card);
			transaction.setAvailableBalance(priceUpdate);
			transaction.setCardNumber(card);
			transaction.setTransactionAmount(price);
			transaction.setTransactionComments(ApplicationConstants.TRANSACTIONCOMMENTS);
			transaction.setTransactionStatus(ApplicationConstants.SUCCESS_MSG);
			transaction.setTransactionTime(LocalDateTime.now());
			transaction.setTransactionType(ApplicationConstants.DEBIT_MESSAGE);
			transactionRepository.save(transaction);
			return true;
		}
		return false;
	}

	/**
	 * This method is used to get monthly transactions of the credit card for a particular year
	 * @author chethana
	 * @param month is of integer dataType
	 * @param year is of integer dataType
	 * @param creditCardId is of long dataType
	 * @return TransactionResponseDto returns a list of transaction summary
	 * 
	 */
	public TransactionResponseDto getMonthlyStatement(int month, int year, Long creditCardId) {
		log.info("Entering into getMonthlyStatement() of TransactionServiceImpl");
		Optional<Card> creditCardResponse = cardRepository.findById(creditCardId);

		if (!creditCardResponse.isPresent()) {
			log.error("Exception Occured in getMonthlyStatement");
			throw new CardException("Invalid credit card");
		}
		Optional<List<Transaction>> transactionResponse = transactionRepository
				.findByCardNumber(creditCardResponse.get());

		if (!transactionResponse.isPresent()) {
			log.error("No Transactions Found");
			throw new NoTransactionException("No Transactions Found");
		}

		List<TransactionSummary> transactionSummaryList = new ArrayList<>();		
		transactionResponse.get().forEach(transaction -> {
			if (transaction.getTransactionTime().getMonthValue() == month
					&& transaction.getTransactionTime().getYear() == year) {
				TransactionSummary transactionSummary = new TransactionSummary();
				BeanUtils.copyProperties(transaction, transactionSummary);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ApplicationConstants.DATE_PATTERN);
				transactionSummary.setTransactionTime(simpleDateFormat.format(new Date()));
				transactionSummaryList.add(transactionSummary);
			}
		});

		TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
		transactionResponseDto.setTransactionList(transactionSummaryList);
		return transactionResponseDto;
	}

}
