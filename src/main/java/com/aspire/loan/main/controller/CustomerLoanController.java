package com.aspire.loan.main.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.loan.main.dto.LoanDto;
import com.aspire.loan.main.service.LoanService;

@RestController
@RequestMapping("/api/customers/{customerId}/loans")
public class CustomerLoanController {

    private final LoanService loanService;

    public CustomerLoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanDto>> getCustomerLoans(@PathVariable Long customerId) {
        // Add policy check to ensure the customer can view their own loans
        List<LoanDto> customerLoans = loanService.getCustomerLoans(customerId);
        return ResponseEntity.ok(customerLoans);
    }
}

