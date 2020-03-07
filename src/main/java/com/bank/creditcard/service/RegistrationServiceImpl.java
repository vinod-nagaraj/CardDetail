package com.bank.creditcard.service;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.dto.RegistrationRequestDto;
import com.bank.creditcard.dto.RegistrationResponseDto;
import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Customer;
import com.bank.creditcard.exception.UnderAgeException;
import com.bank.creditcard.repository.CardRepository;
import com.bank.creditcard.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana
 * 
 *This class provides all the operations related to end user like registering into portal and apply credit card
 *
 */
@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	CustomerRepository customerRepository;

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
	public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) throws UnderAgeException {
		log.info("Entering into register() of RegistrationServiceImpl");

		if (LocalDate.now().getYear() - registrationRequestDto.getDateOfBirth().getYear() < 23) {
			log.error("Exception occured in register() of RegistrationServiceImpl:" + ApplicationConstants.UNDER_AGE);
			throw new UnderAgeException(ApplicationConstants.UNDER_AGE);
		}

		Card card = new Card();
		Customer customer = new Customer();
		Random random = new Random();
		BeanUtils.copyProperties(registrationRequestDto, customer);
		customer.setPassword("" + random.nextInt(9999));
		customer = customerRepository.save(customer);

		BeanUtils.copyProperties(registrationRequestDto, card);
		card.setCardType(ApplicationConstants.TYPE);
		card.setCustomerId(customer);
		card.setCvv(random.nextInt(100));
		card.setValidFrom(LocalDate.now());
		card.setValidTo(LocalDate.now().plusMonths(ApplicationConstants.VALID_MONTHS));
		cardRepository.save(card);

		RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto();
		registrationResponseDto.setCustomerId(customer.getCustomerEmail());
		registrationResponseDto.setPassword(customer.getPassword());
		return registrationResponseDto;

	}
}
