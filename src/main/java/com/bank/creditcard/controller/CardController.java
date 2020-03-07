package com.bank.creditcard.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.dto.RegistrationRequestDto;
import com.bank.creditcard.dto.RegistrationResponseDto;
import com.bank.creditcard.exception.DateInvalidException;
import com.bank.creditcard.exception.InsufficientBalanceException;
import com.bank.creditcard.exception.UnderAgeException;
import com.bank.creditcard.service.CardService;
import com.bank.creditcard.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to perform all the operations related to the credit card
 * of the particular bank like registration
 * 
 * @author Chethana
 * 
 **/
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/cards")
@Slf4j
public class CardController {

	@Autowired
	RegistrationService registrationService;

	@Autowired
	CardService cardService;

	/**
	 * This Method allows the end user to provide his basic details and register a
	 * new credit card
	 * 
	 * @author Chethana
	 * @param registrationRequestDto contains the basic details from used to claim
	 *                               credit card
	 * @return RegistrationResponseDto returns success/failure along with the
	 *         created credit card account login details
	 * @throws UnderAgeException thrown when the user is below 23 years
	 */
	@PostMapping
	public ResponseEntity<RegistrationResponseDto> register(
			@Valid @RequestBody RegistrationRequestDto registrationRequestDto) throws UnderAgeException {
		log.info("Entering into registerCustomer method of registrationService ");
		RegistrationResponseDto registrationResponseDto = registrationService.register(registrationRequestDto);
		if (Objects.isNull(registrationResponseDto)) {
			RegistrationResponseDto registrationResponse = new RegistrationResponseDto();
			registrationResponse.setMessage(ApplicationConstants.FAILURE_MSG);
			registrationResponse.setStatusCode(ApplicationConstants.FAILURE_CODE);
			return new ResponseEntity<>(registrationResponseDto, HttpStatus.NOT_FOUND);
		}
		registrationResponseDto.setMessage(ApplicationConstants.SUCCESS_MSG);
		registrationResponseDto.setStatusCode(ApplicationConstants.SUCCESS_CODE);
		return new ResponseEntity<>(registrationResponseDto, HttpStatus.OK);
	}

	/**
	 * @author Muthu
	 * 
	 *         Method is used for checking whether a user can purchase based on the
	 *         card number and balance
	 * 
	 * @param cardNumber
	 * @param cvv
	 * @param price
	 * @return true/false
	 * @throws DateInvalidException
	 * @throws InsufficientBalanceException
	 */
	@GetMapping
	public Boolean cardDetails(@RequestParam Long cardNumber, @RequestParam Integer cvv, @RequestParam Double price)
			throws DateInvalidException, InsufficientBalanceException {
		return cardService.getCardDetails(cardNumber, cvv, price);

	}

}
