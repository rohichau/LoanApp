package com.aspire.loan.main.request;

import java.math.BigDecimal;

public class RepaymentRequest {
	private Long loanId;
	private BigDecimal amount;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
