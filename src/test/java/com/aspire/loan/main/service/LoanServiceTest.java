package com.aspire.loan.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.aspire.loan.main.dto.LoanDto;
import com.aspire.loan.main.enums.LoanState;
import com.aspire.loan.main.enums.RepaymentState;
import com.aspire.loan.main.model.Customer;
import com.aspire.loan.main.model.Loan;
import com.aspire.loan.main.model.Repayment;
import com.aspire.loan.main.repository.CustomerRepository;
import com.aspire.loan.main.repository.LoanRepository;
import com.aspire.loan.main.request.LoanRequest;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private RepaymentService repaymentService;
    
    @Mock
    private CustomerRepository custRepository;

    private LoanService loanService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        loanService = new LoanService(loanRepository, repaymentService,custRepository);
    }

    @Test
    void createLoan_ValidLoanRequest_SuccessfullyCreatesLoan() {
        // Arrange
        LoanRequest loanRequest = new LoanRequest(BigDecimal.valueOf(10000), 3, LocalDate.of(2022, 2, 7),1L);
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("default");
        
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setAmount(BigDecimal.valueOf(10000));
        loan.setTerm(3);
        loan.setCreationDate(LocalDate.of(2022, 2, 7));
        loan.setState(LoanState.PENDING);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(custRepository.findById(1L)).thenReturn(cust);
        when(custRepository.save(any(Customer.class))).thenReturn(cust);

        // Act
        LoanDto createdLoan = loanService.createLoan(loanRequest);

        // Assert
        assertEquals(1L, createdLoan.getId());
        assertEquals(BigDecimal.valueOf(10000), createdLoan.getAmount());
        assertEquals(3, createdLoan.getTerm());
        assertEquals(LocalDate.of(2022, 2, 7), createdLoan.getCreationDate());
        assertEquals(LoanState.PENDING.toString(), createdLoan.getState());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void approveLoan_ExistingLoanId_SuccessfullyApprovesLoan() throws NotFoundException {
        // Arrange
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setState(LoanState.PENDING);
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("ROHIT");
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(custRepository.findById(1L)).thenReturn(cust);

        // Act
        LoanDto approvedLoan = loanService.approveLoan(loanId);

        // Assert
        assertEquals(loanId, approvedLoan.getId());
        assertEquals(LoanState.APPROVED.toString(), approvedLoan.getState());
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void approveLoan_NonexistentLoanId_ThrowsNotFoundException() {
        // Arrange
        Long loanId = 1L;
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("ROHIT");
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());
        when(custRepository.findById(1L)).thenReturn(cust);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> loanService.approveLoan(loanId));
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void getCustomerLoans_ValidCustomerId_ReturnsCustomerLoans() {
        // Arrange
    	
    	Customer customer1 = new Customer();
    	Customer customer2 = new Customer();
    	
    	customer1.setId(1L);
    	customer1.setName("Balram");
    	customer2.setId(2L);
    	customer2.setName("Mukesh");
    	
        Long customerId = 1L;
        List<Loan> loans = new ArrayList<>();
        Loan loan1 = new Loan();
        loan1.setId(1L);
        loan1.setCustomer(customer1);
        loan1.setState(LoanState.PENDING);
        Loan loan2 = new Loan();
        loan2.setId(2L);
        loan2.setCustomer(customer2);
        loan2.setState(LoanState.APPROVED);
        loans.add(loan1);
        loans.add(loan2);
        when(loanRepository.findByCustomerId(customerId)).thenReturn(loans);

        // Act
        List<LoanDto> customerLoans = loanService.getCustomerLoans(customerId);

        // Assert
        assertEquals(2, customerLoans.size());
        assertEquals(1L, customerLoans.get(0).getId());
        assertEquals(2L, customerLoans.get(1).getId());
        verify(loanRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void generateScheduledRepayments_ValidLoanRequest_ReturnsRepaymentList() {
        // Arrange
        LoanRequest loanRequest = new LoanRequest(BigDecimal.valueOf(10000), 3, LocalDate.of(2022, 2, 7),1L);

        // Act
        List<Repayment> repayments = loanService.generateScheduledRepayments(loanRequest);

        // Assert
        assertEquals(3, repayments.size());
        assertEquals(BigDecimal.valueOf(3333.33), repayments.get(0).getAmount());
        assertEquals(BigDecimal.valueOf(3333.33), repayments.get(1).getAmount());
        assertEquals(BigDecimal.valueOf(3333.33), repayments.get(2).getAmount());
        assertEquals(LocalDate.of(2022, 2, 14), repayments.get(0).getDueDate());
        assertEquals(LocalDate.of(2022, 2, 21), repayments.get(1).getDueDate());
        assertEquals(LocalDate.of(2022, 2, 28), repayments.get(2).getDueDate());
        assertEquals(RepaymentState.PENDING, repayments.get(0).getState());
        assertEquals(RepaymentState.PENDING, repayments.get(1).getState());
        assertEquals(RepaymentState.PENDING, repayments.get(2).getState());
    }
}
