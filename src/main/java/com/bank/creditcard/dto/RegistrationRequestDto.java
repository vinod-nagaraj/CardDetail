package com.bank.creditcard.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDto {
	
	private String customerEmail;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
	private Long mobile;
	private Double salary;
	private String profession;
	private Double cardBalance;
}
