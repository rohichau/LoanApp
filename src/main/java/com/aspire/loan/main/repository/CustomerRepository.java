package com.aspire.loan.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.loan.main.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer save(Customer user);

	Customer findById(long l);

}
