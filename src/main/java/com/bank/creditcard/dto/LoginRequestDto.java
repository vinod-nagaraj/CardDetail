package com.bank.creditcard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

	private String customerEmail;
	private String password;
}
