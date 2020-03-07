package com.bank.creditcard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.creditcard.entity.Card;
import com.bank.creditcard.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Optional<List<Transaction>> findByCardNumber(Card card);

}
