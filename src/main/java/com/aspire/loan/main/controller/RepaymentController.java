package com.aspire.loan.main.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.loan.main.dto.RepaymentDto;
import com.aspire.loan.main.request.RepaymentRequest;
import com.aspire.loan.main.service.RepaymentService;

@RestController
@RequestMapping("/api/customers/{customerId}/loans/{loanId}/repayments")
public class RepaymentController {

	private final RepaymentService repaymentService;

	public RepaymentController(RepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	@PostMapping
	public ResponseEntity<RepaymentDto> addRepayment(@PathVariable Long customerId, @PathVariable Long loanId,
			@RequestBody RepaymentRequest repaymentRequest) {
		// Validate and process the repayment request
		try {
			RepaymentDto repayment = repaymentService.addRepayment(customerId, loanId, repaymentRequest);
			return ResponseEntity.ok(repayment);
		} catch (NotFoundException e) {
			// Handle NotFoundException appropriately
			return ResponseEntity.notFound().build();
		}
	}
}
