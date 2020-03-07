package com.bank.creditcard.service;

import com.bank.creditcard.dto.LoginRequestDto;
import com.bank.creditcard.dto.LoginResponseDto;
import com.bank.creditcard.exception.UserException;


public interface LoginService {
	public LoginResponseDto login(LoginRequestDto loginRequestdto) throws UserException;

}
