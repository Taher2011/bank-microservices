package com.udemy.loan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.udemy.loan.exception.LoanServiceException;
import com.udemy.loan.model.LoanDTO;
import com.udemy.loan.service.LoanService;

@Controller
@RequestMapping("/v1/loans")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		super();
		this.loanService = loanService;
	}

	@GetMapping("/{customer-id}")
	public ResponseEntity<List<LoanDTO>> getLoansForCustomer(
			@RequestHeader(required = false, name = "bank-correlation-id") String correlationid,
			@PathVariable(name = "customer-id") int customerId) throws LoanServiceException {
		return new ResponseEntity<>(loanService.getLoansForCustomer(correlationid, customerId), HttpStatus.OK);
	}

	@PostMapping("/{customer-id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLoan(@RequestBody LoanDTO loan) {
		loanService.createLoan(loan);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLoans(@RequestBody List<LoanDTO> loans) {
		loanService.createLoans(loans);
	}

	@GetMapping("/")
	public ResponseEntity<List<LoanDTO>> getLoans() {
		return new ResponseEntity<>(loanService.getLoans(), HttpStatus.OK);
	}

	@GetMapping("/properties")
	public ResponseEntity<String> getLoansProperties() throws JsonProcessingException {
		return new ResponseEntity<>(loanService.getLoansProperties(), HttpStatus.OK);
	}

}
