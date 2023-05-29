package com.aspire.loan.main.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.aspire.loan.main.Exceptions.LoanCreationException;
import com.aspire.loan.main.Exceptions.LoanProcessingException;
import com.aspire.loan.main.Exceptions.LoanRetrievalException;
import com.aspire.loan.main.dto.LoanDto;
import com.aspire.loan.main.dto.RepaymentDto;
import com.aspire.loan.main.enums.LoanState;
import com.aspire.loan.main.enums.RepaymentState;
import com.aspire.loan.main.model.Customer;
import com.aspire.loan.main.model.Loan;
import com.aspire.loan.main.model.Repayment;
import com.aspire.loan.main.repository.CustomerRepository;
import com.aspire.loan.main.repository.LoanRepository;
import com.aspire.loan.main.request.LoanRequest;

@Service
public class LoanService {

	private final LoanRepository loanRepository;
	private final RepaymentService repaymentService;
	private final CustomerRepository custRepository;
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoanService.class);


	public LoanService(LoanRepository loanRepository, RepaymentService repaymentService,
			CustomerRepository custRepository) {
		this.loanRepository = loanRepository;
		this.repaymentService = repaymentService;
		this.custRepository = custRepository;
	}

	public LoanDto createLoan(LoanRequest loanRequest) {
		try {
			// Validate loan request and process loan creation
			Loan loan = new Loan();
			loan.setAmount(loanRequest.getAmount());
			loan.setTerm(loanRequest.getTerm());
			loan.setCreationDate(loanRequest.getDate());
			loan.setState(LoanState.PENDING);

			Optional<Customer> cust = custRepository.findById(loanRequest.getCustID());
			Customer toSet = new Customer();
			if (cust.isEmpty()) {
				toSet = new Customer();
				toSet.setId(1L);
				toSet.setName("default");
				custRepository.save(toSet);
			} else {
				toSet = cust.get();
			}
			loan.setCustomer(toSet);

			List<Repayment> repayments = generateScheduledRepayments(loanRequest);
			// Set the loan reference for each repayment
			for (Repayment repayment : repayments) {
				repayment.setLoan(loan);
			}
			loan.setRepayments(repayments);

			Loan loanCreated = loanRepository.save(loan);
			loanCreated.setCustomer(toSet);
			return mapToDto(loanCreated);
		} catch (Exception e) {
			logger.error("Error occurred while creating loan: {}", e.getMessage());
			throw new LoanCreationException("Failed to create loan");
		}
	}

	public LoanDto approveLoan(Long loanId) throws NotFoundException {
		try {
			Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException());
			loan.setState(LoanState.APPROVED);
			Loan approvedLoan = loanRepository.save(loan);
			return mapToDto(approvedLoan);
		} catch (NotFoundException e) {
			logger.error("Loan not found with ID: {}", loanId);
			throw e;
		} catch (Exception e) {
			logger.error("Error occurred while approving loan: {}", e.getMessage());
			throw new LoanProcessingException("Failed to approve loan");
		}
	}

	public List<LoanDto> getCustomerLoans(Long customerId) {
		try {
			// Implement policy check to ensure the customer can view their own loans
			List<Loan> loanList = loanRepository.findByCustomerId(customerId);
			List<LoanDto> loanDtoList = new ArrayList<>();
			for (Loan loan : loanList) {
				loanDtoList.add(mapToDto(loan));
			}
			return loanDtoList;
		} catch (Exception e) {
			logger.error("Error occurred while retrieving customer loans: {}", e.getMessage());
			throw new LoanRetrievalException("Failed to retrieve customer loans");
		}
	}

	List<Repayment> generateScheduledRepayments(LoanRequest loanRequest) {
		List<Repayment> repayments = new ArrayList<>();
		BigDecimal repaymentAmount = loanRequest.getAmount().divide(BigDecimal.valueOf(loanRequest.getTerm()), 2,
				RoundingMode.HALF_UP);

		LocalDate repaymentDate = loanRequest.getDate().plusWeeks(1); // Start from the following week
		for (int i = 0; i < loanRequest.getTerm(); i++) {
			Repayment repayment = new Repayment();
			repayment.setAmount(repaymentAmount);
			repayment.setDueDate(repaymentDate);
			repayment.setState(RepaymentState.PENDING);
			repayments.add(repayment);

			repaymentDate = repaymentDate.plusWeeks(1);
		}

		return repayments;
	}

	private static LoanDto mapToDto(Loan loan) {
		LoanDto loanDto = new LoanDto();
		loanDto.setId(loan.getId());
		loanDto.setAmount(loan.getAmount());
		loanDto.setTerm(loan.getTerm());
		if (loan.getCustomer() != null) {
			loanDto.setCustomerId(loan.getCustomer().getId());
			loanDto.setCustomerName(loan.getCustomer().getName());
		}
		if (loan.getState() != null)
			loanDto.setState(loan.getState().toString());
		loanDto.setCreationDate(loan.getCreationDate());

		List<RepaymentDto> repaymentDtos = new ArrayList<>();
		for (Repayment repayment : loan.getRepayments()) {
			RepaymentDto repaymentDto = new RepaymentDto();
			repaymentDto.setId(repayment.getId());
			repaymentDto.setAmount(repayment.getAmount());
			repaymentDto.setDueDate(repayment.getDueDate());
			repaymentDto.setPaymentDate(repayment.getPaymentDate());
			repaymentDto.setState(repayment.getState());
			if (repayment.getLoan() != null)
				repaymentDto.setLoanId(repayment.getLoan().getId());
			repaymentDtos.add(repaymentDto);
		}
		loanDto.setRepayments(repaymentDtos);

		return loanDto;
	}
}
