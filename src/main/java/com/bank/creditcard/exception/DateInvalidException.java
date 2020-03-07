package com.bank.creditcard.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateInvalidException extends Exception{

	private static final long serialVersionUID = 1L;

	public DateInvalidException(String s) {
		super(s);
	}
}
