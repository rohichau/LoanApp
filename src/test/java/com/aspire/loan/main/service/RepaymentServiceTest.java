package com.aspire.loan.main.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.aspire.loan.main.dto.RepaymentDto;
import com.aspire.loan.main.enums.LoanState;
import com.aspire.loan.main.enums.RepaymentState;
import com.aspire.loan.main.model.Loan;
import com.aspire.loan.main.model.Repayment;
import com.aspire.loan.main.repository.LoanRepository;
import com.aspire.loan.main.repository.RepaymentRepository;
import com.aspire.loan.main.request.RepaymentRequest;

public class RepaymentServiceTest {

	private RepaymentService repaymentService;

	@Mock
	private RepaymentRepository repaymentRepository;

	@Mock
	private LoanRepository loanRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		repaymentService = new RepaymentService(repaymentRepository, loanRepository);
	}

	@Test
	public void testAddRepayment_SuccessfulPayment() throws NotFoundException {
		// Mock loan and repayment data
		Long customerId = 1L;
		Long loanId = 1L;
		BigDecimal repaymentAmount = BigDecimal.valueOf(3333.33);
		BigDecimal paymentAmount = BigDecimal.valueOf(3333.33);
		LocalDate paymentDate = LocalDate.now();

		Loan loan = new Loan();
		loan.setId(loanId);
		loan.setState(LoanState.PENDING);

		Repayment scheduledRepayment = new Repayment();
		scheduledRepayment.setAmount(repaymentAmount);
		scheduledRepayment.setState(RepaymentState.PENDING);

		loan.setRepayments(List.of(scheduledRepayment));

		RepaymentRequest repaymentRequest = new RepaymentRequest();
		repaymentRequest.setAmount(paymentAmount);

		// Mock repository methods
		when(loanRepository.findByIdAndCustomerId(loanId, customerId)).thenReturn(Optional.of(loan));
		when(repaymentRepository.save(any(Repayment.class))).thenReturn(new Repayment());

		// Call the service method
		RepaymentDto result = repaymentService.addRepayment(customerId, loanId, repaymentRequest);

		// Verify the method invocations
		verify(loanRepository).findByIdAndCustomerId(loanId, customerId);
		verify(loanRepository).save(loan);

		// Assert the results
		Assertions.assertEquals(paymentAmount, result.getAmount());
		Assertions.assertEquals(paymentDate, result.getPaymentDate());
		Assertions.assertEquals(RepaymentState.PAID, result.getState());
		Assertions.assertEquals(RepaymentState.PAID, scheduledRepayment.getState());
		Assertions.assertEquals(LoanState.PAID, loan.getState());
	}

	@Test
	public void testAddRepayment_NoPendingRepayments() throws NotFoundException {
		// Mock loan and repayment data
		Long customerId = 1L;
		Long loanId = 1L;

		Loan loan = new Loan();
		loan.setId(loanId);
		loan.setState(LoanState.PENDING);
		loan.setRepayments(new ArrayList<>());

		RepaymentRequest repaymentRequest = new RepaymentRequest();
		repaymentRequest.setAmount(BigDecimal.valueOf(3333.33));

		// Mock repository methods
		when(loanRepository.findByIdAndCustomerId(loanId, customerId)).thenReturn(Optional.of(loan));

		// Call the service method and assert the exception
		Assertions.assertThrows(IllegalStateException.class,
				() -> repaymentService.addRepayment(customerId, loanId, repaymentRequest));

		// Verify the method invocations
		verify(loanRepository).findByIdAndCustomerId(loanId, customerId);
		verify(repaymentRepository, never()).save(any(Repayment.class));
		verify(loanRepository, never()).save(any(Loan.class));
	}

	@Test
	public void testAddRepayment_InvalidRepaymentAmount() throws NotFoundException {
		// Mock loan and repayment data
		Long customerId = 1L;
		Long loanId = 1L;
		BigDecimal repaymentAmount = BigDecimal.valueOf(3333.33);
		BigDecimal paymentAmount = BigDecimal.valueOf(2000.00);

		Loan loan = new Loan();
		loan.setId(loanId);
		loan.setState(LoanState.PENDING);

		Repayment scheduledRepayment = new Repayment();
		scheduledRepayment.setAmount(repaymentAmount);
		scheduledRepayment.setState(RepaymentState.PENDING);

		loan.setRepayments(List.of(scheduledRepayment));

		RepaymentRequest repaymentRequest = new RepaymentRequest();
		repaymentRequest.setAmount(paymentAmount);

		// Mock repository methods
		when(loanRepository.findByIdAndCustomerId(loanId, customerId)).thenReturn(Optional.of(loan));

		// Call the service method and assert the exception
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> repaymentService.addRepayment(customerId, loanId, repaymentRequest));

		// Verify the method invocations
		verify(loanRepository).findByIdAndCustomerId(loanId, customerId);
		verify(repaymentRepository, never()).save(any(Repayment.class));
		verify(loanRepository, never()).save(any(Loan.class));
	}
}
