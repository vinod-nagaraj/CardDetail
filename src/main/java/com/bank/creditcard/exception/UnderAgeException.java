package com.bank.creditcard.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnderAgeException extends Exception{

	private static final long serialVersionUID = 1L;

	public UnderAgeException(String s) {
		super(s);
	}
}
