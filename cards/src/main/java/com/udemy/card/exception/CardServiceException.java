package com.udemy.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	private final String message;

}
