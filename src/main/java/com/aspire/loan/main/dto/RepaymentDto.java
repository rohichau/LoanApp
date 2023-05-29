package com.aspire.loan.main.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.aspire.loan.main.enums.RepaymentState;

public class RepaymentDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private RepaymentState state;
    private Long loanId;

    // Constructors, getters, and setters

    public RepaymentDto() {
    }

    public RepaymentDto(Long id, BigDecimal amount, LocalDate dueDate, LocalDate paymentDate, RepaymentState state, Long loanId) {
        this.id = id;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.state = state;
        this.loanId = loanId;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public RepaymentState getState() {
        return state;
    }

    public void setState(RepaymentState state) {
        this.state = state;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
}
