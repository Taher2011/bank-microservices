package com.udemy.loan.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	CUSTOMER_NOT_FOUND(101, "Customer with id '%s' not found", HttpStatus.NOT_FOUND),
	CUSTOMER_WITH_LOAN_NUMBER_NOT_FOUND(102, "customer with id '%s' and loan-number '%s' not found",
			HttpStatus.NOT_FOUND),
	INTERNAL_SERVER_ERROR(501, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

	private int code;
	private String message;
	HttpStatus httpStatus;

	private ErrorCode(int code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

}
