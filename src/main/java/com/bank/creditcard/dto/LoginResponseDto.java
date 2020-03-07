package com.bank.creditcard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	private Integer statusCode;
	private String message;
	private Long customerID;
	private String customerName;
	private Long creditCardId;
	private String loginType;
}
