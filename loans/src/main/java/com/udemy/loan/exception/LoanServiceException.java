package com.udemy.loan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	private final String message;

}
