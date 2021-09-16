package com.udemy.loan.exception;

import lombok.Getter;

@Getter
public class LoanServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	public LoanServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
