package com.aspire.loan.main.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.loan.main.dto.LoanDto;
import com.aspire.loan.main.service.LoanService;

@RestController
@RequestMapping("/api/admin/loans")
public class AdminLoanController {

	private final LoanService loanService;

	public AdminLoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PutMapping("/{loanId}/approve")
	public ResponseEntity<LoanDto> approveLoan(@PathVariable Long loanId) {
		try {
			LoanDto approvedLoan = loanService.approveLoan(loanId);
			return ResponseEntity.ok(approvedLoan);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
}
