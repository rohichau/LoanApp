package com.aspire.loan.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.loan.main.dto.LoanDto;
import com.aspire.loan.main.request.LoanRequest;
import com.aspire.loan.main.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanRequest loanRequest) {
        // Validate and process the loan request
        // Calculate scheduled repayments
        // Set loan and repayment states to PENDING
        LoanDto loan = loanService.createLoan(loanRequest);
        return ResponseEntity.ok(loan);
    }
}

