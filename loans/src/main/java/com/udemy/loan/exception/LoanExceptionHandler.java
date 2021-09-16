package com.udemy.loan.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.udemy.loan.model.ErrorDTO;

@ControllerAdvice
public class LoanExceptionHandler {

	@ExceptionHandler(LoanServiceException.class)
	public ResponseEntity<ErrorDTO> accountException(LoanServiceException e) {
		ErrorDTO error = new ErrorDTO();
		error.setCode(e.getErrorCode().getCode());
		error.setMessage(e.getErrorCode().getMessage());
		return new ResponseEntity<>(error, e.getErrorCode().getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> genericException(Exception e) {
		ErrorDTO error = new ErrorDTO();
		error.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		error.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		return new ResponseEntity<>(error, ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus());
	}

}
