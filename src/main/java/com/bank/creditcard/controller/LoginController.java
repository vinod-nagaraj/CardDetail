package com.bank.creditcard.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.creditcard.constants.ApplicationConstants;
import com.bank.creditcard.dto.LoginRequestDto;
import com.bank.creditcard.dto.LoginResponseDto;
import com.bank.creditcard.exception.UserException;
import com.bank.creditcard.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana M
 * @Description This class is used to perform all the customer related
 *              authentication operations
 *
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/customers")
@Slf4j
public class LoginController {

	@Autowired
	LoginService loginService;

	

	/**
	 * This method allows the customer to login to mybank application
	 * 
	 * @author chethana
	 * @param loginRequestdto takes customerId and password
	 * @return LoginResponseDto success/failure message
	 * @throws GeneralException if the valid credentials are not present
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestdto)
			throws UserException {
		log.info("Entering into login method of LoginController");
		LoginResponseDto loginResponsedto = loginService.login(loginRequestdto);
		if (Objects.isNull(loginResponsedto)) {
			LoginResponseDto loginResponse = new LoginResponseDto();
			loginResponse.setMessage(ApplicationConstants.FAILURE_MSG);
			loginResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(loginResponse, HttpStatus.NOT_FOUND);
		}
		loginResponsedto.setMessage(ApplicationConstants.SUCCESS_MSG);
		loginResponsedto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(loginResponsedto, HttpStatus.OK);

	}

}
