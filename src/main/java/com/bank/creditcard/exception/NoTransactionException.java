package com.bank.creditcard.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoTransactionException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NoTransactionException(String s) {
		super(s);
	}
}
