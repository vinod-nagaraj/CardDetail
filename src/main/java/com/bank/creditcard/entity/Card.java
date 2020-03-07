package com.bank.creditcard.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "creditIdSequencer", initialValue = 1000000001, allocationSize = 1)
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creditIdSequencer")
	private Long cardNumber;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customerId;
	
	private String cardType;
	private Double cardBalance;
	private LocalDate validFrom;
	private LocalDate validTo;
	private Integer cvv;
	
}
