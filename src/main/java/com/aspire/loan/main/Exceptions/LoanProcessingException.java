package com.aspire.loan.main.Exceptions;

public class LoanProcessingException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 521244391105914476L;

	public LoanProcessingException(String message) {
        super(message);
    }

    public LoanProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
