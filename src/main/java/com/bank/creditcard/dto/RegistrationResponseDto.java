package com.bank.creditcard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponseDto {
	private String message;
	private Integer statusCode;
	private String password;
	private String customerId;
}
