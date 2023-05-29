package com.aspire.loan.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.loan.main.model.Repayment;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {

}
