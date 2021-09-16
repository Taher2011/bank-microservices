package com.udemy.user.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	public UserServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
