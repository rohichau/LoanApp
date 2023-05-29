package com.aspire.loan.main.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.aspire.loan.main.enums.LoanState;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan")
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "term")
	private int term;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private LoanState state;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
	private List<Repayment> repayments = new ArrayList<>();

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LoanState getState() {
		return state;
	}

	public void setState(LoanState state) {
		this.state = state;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public List<Repayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<Repayment> repayments) {
		this.repayments = repayments;
	}

	public void addRepayment(Repayment repayment) {
		repayment.setLoan(this);
		repayments.add(repayment);
	}

	public void removeRepayment(Repayment repayment) {
		repayment.setLoan(null);
		repayments.remove(repayment);
	}
}
