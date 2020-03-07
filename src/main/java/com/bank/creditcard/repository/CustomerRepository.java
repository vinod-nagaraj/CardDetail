package com.bank.creditcard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.creditcard.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByCustomerEmailAndPassword(String mailId,String password);

	Customer findByCustomerId(Long customerId);
}
