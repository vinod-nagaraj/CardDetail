package com.bank.creditcard.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientBalanceException extends Exception{

	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(String s) {
		super(s);
	}
}
