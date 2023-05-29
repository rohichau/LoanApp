package com.aspire.loan.main.Exceptions;

public class LoanRetrievalException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5751923712739222430L;

	public LoanRetrievalException(String message) {
        super(message);
    }

    public LoanRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
