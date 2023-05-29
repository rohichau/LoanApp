package com.aspire.loan.main.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDto {
    private Long id;
    private BigDecimal amount;
    private int term;
    private Long customerId;
    private String customerName;
    private String state;
    private LocalDate creationDate;
    private List<RepaymentDto> repayments = new ArrayList<>();

    // Constructors, getters, and setters

    public LoanDto() {
    }

    public LoanDto(Long id, BigDecimal amount, int term, Long customerId, String customerName, String state, LocalDate creationDate, List<RepaymentDto> repayments) {
        this.id = id;
        this.amount = amount;
        this.term = term;
        this.customerId = customerId;
        this.customerName = customerName;
        this.state = state;
        this.creationDate = creationDate;
        this.repayments = repayments;
    }

    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<RepaymentDto> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<RepaymentDto> repayments) {
        this.repayments = repayments;
    }
}
