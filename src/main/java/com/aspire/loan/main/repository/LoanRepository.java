package com.aspire.loan.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.loan.main.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomerId(Long customerId);

    Optional<Loan> findByIdAndCustomerId(Long loanId, Long customerId);
}
