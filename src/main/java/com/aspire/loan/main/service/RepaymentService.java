package com.aspire.loan.main.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.aspire.loan.main.dto.RepaymentDto;
import com.aspire.loan.main.enums.LoanState;
import com.aspire.loan.main.enums.RepaymentState;
import com.aspire.loan.main.model.Loan;
import com.aspire.loan.main.model.Repayment;
import com.aspire.loan.main.repository.LoanRepository;
import com.aspire.loan.main.repository.RepaymentRepository;
import com.aspire.loan.main.request.RepaymentRequest;

@Service
public class RepaymentService {

	private final RepaymentRepository repaymentRepository;
	private final LoanRepository loanRepository;

	public RepaymentService(RepaymentRepository repaymentRepository, LoanRepository loanRepository) {
		this.repaymentRepository = repaymentRepository;
		this.loanRepository = loanRepository;
	}

	public RepaymentDto addRepayment(Long customerId, Long loanId, RepaymentRequest repaymentRequest)
			throws NotFoundException {
		Loan loan = loanRepository.findByIdAndCustomerId(loanId, customerId).orElseThrow(() -> new NotFoundException());

		// Find the corresponding scheduled repayment
		Optional<Repayment> scheduledRepaymentOptional = loan.getRepayments().stream()
				.filter(r -> r.getState() == RepaymentState.PENDING).findFirst();

		if (scheduledRepaymentOptional.isEmpty()) {
			throw new IllegalStateException("No pending repayments found for the loan");
		}

		Repayment scheduledRepayment = scheduledRepaymentOptional.get();

		if (repaymentRequest.getAmount().compareTo(scheduledRepayment.getAmount()) < 0) {
			throw new IllegalArgumentException(
					"Repayment amount must be greater than or equal to the scheduled amount");
		}

		scheduledRepayment.setState(RepaymentState.PAID);
		scheduledRepayment.setPaymentDate(LocalDate.now());
		Repayment repayment = new Repayment();
		repayment.setAmount(repaymentRequest.getAmount());
		repayment.setPaymentDate(LocalDate.now());
		repayment.setState(RepaymentState.PAID);
		repayment.setLoan(loan);

		boolean allRepaymentsPaid = loan.getRepayments().stream().allMatch(r -> r.getState() == RepaymentState.PAID);
		if (allRepaymentsPaid) {
			loan.setState(LoanState.PAID);
		}
		loanRepository.save(loan);

		return mapToDto(repayment);
	}

	private static RepaymentDto mapToDto(Repayment repayment) {
		RepaymentDto repaymentDto = new RepaymentDto();
		repaymentDto.setId(repayment.getId());
		repaymentDto.setAmount(repayment.getAmount());
		repaymentDto.setDueDate(repayment.getDueDate());
		repaymentDto.setPaymentDate(repayment.getPaymentDate());
		repaymentDto.setState(repayment.getState());
		repaymentDto.setLoanId(repayment.getLoan().getId());

		return repaymentDto;
	}
}
