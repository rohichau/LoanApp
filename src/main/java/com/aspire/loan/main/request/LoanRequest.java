package com.aspire.loan.main.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanRequest {
	public LoanRequest(BigDecimal amount, int term, LocalDate date, long l) {
		super();
		this.amount = amount;
		this.term = term;
		this.date = date;
		this.custID = l;
	}

	private BigDecimal amount;
	private int term;
	private LocalDate date;
	private Long custID;

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getCustID() {
		return custID;
	}

	public void setCustID(Long custID) {
		this.custID = custID;
	}

}
