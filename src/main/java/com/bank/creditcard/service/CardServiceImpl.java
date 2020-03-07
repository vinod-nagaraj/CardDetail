package com.bank.creditcard.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.entity.Card;
import com.bank.creditcard.exception.DateInvalidException;
import com.bank.creditcard.exception.InsufficientBalanceException;
import com.bank.creditcard.repository.CardRepository;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	CardRepository cardRepository;

	/**
	 * @author Muthu
	 * 
	 *         Method is used for checking whether a user can purchase based on the
	 *         card number and balance
	 *         
	 * @param cardNumber
	 * @param cvv
	 * @param price
	 * @return
	 * @throws DateInvalidException
	 * @throws InsufficientBalanceException 
	 */
	@Override
	public Boolean getCardDetails(Long cardNumber, Integer cvv, Double price) throws DateInvalidException, InsufficientBalanceException {
		Optional<Card> card = cardRepository.findByCardNumberAndCvv(cardNumber, cvv);
		if (card.isPresent()) {
			LocalDate currentDate = LocalDate.now();
			if (!(currentDate.isBefore(card.get().getValidTo())
					&& (currentDate.isAfter(card.get().getValidFrom()) || currentDate.equals(currentDate)))) {
				throw new DateInvalidException(ApplicationConstants.DATE_INVALIDMESSAGE);
			}
			if (price > card.get().getCardBalance()) {
				throw new InsufficientBalanceException(ApplicationConstants.INSUFFICICENTBALANCE);
			}
			return ApplicationConstants.TRUE;
		}
		return ApplicationConstants.FALSE;
	}

}
