package com.aspire.loan.main.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.aspire.loan.main.enums.RepaymentState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "repayment")
public class Repayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "due_date")
	private LocalDate dueDate;
	
	@Column(name = "payment_date")
	private LocalDate paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private RepaymentState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_id")
	private Loan loan;

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

	public RepaymentState getState() {
		return state;
	}

	public void setState(RepaymentState state) {
		this.state = state;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

}
