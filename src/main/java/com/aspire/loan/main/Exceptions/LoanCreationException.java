package com.aspire.loan.main.Exceptions;

public class LoanCreationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6293296643824713120L;

	public LoanCreationException(String message) {
        super(message);
    }

    public LoanCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}




