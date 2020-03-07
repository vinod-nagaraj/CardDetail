package com.bank.creditcard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Customer;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{

	Optional<Card> findByCardNumberAndCvv(Long cardNumber, Integer cvv);

	Card findByCardNumber(Long cardNumber);

	Optional<Card> findByCustomerId(Customer customer);

}
