package com.bank.creditcard.service;

import com.bank.creditcard.dto.RegistrationRequestDto;
import com.bank.creditcard.dto.RegistrationResponseDto;
import com.bank.creditcard.exception.UnderAgeException;

public interface RegistrationService {
	public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) throws UnderAgeException;
}
