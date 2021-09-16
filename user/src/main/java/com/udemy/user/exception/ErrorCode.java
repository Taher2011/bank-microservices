package com.udemy.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	USER_NOT_FOUND(101, "User id not found", HttpStatus.NOT_FOUND),
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
