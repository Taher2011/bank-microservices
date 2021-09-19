package com.udemy.loan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@GetMapping("/customers")
	public ResponseEntity<List<LoanDTO>> getLoans() {
		return new ResponseEntity<>(loanService.getLoans(), HttpStatus.OK);
	}

	@GetMapping("/customers/{customer-id}")
	public ResponseEntity<List<LoanDTO>> getLoansForCustomer(
			@RequestHeader(required = false, name = "trace-id") String traceId,
			@PathVariable(name = "customer-id") int customerId) throws LoanServiceException {
		return new ResponseEntity<>(loanService.getLoansForCustomer(traceId, customerId), HttpStatus.OK);
	}

	@PostMapping("/customers/{customer-id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLoan(@PathVariable(name = "customer-id") int customerId, @RequestBody LoanDTO loan) {
		loanService.createLoan(customerId, loan);
	}

	@DeleteMapping("/customers/{customer-id}/loan/{loan-number}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAccount(@PathVariable(name = "customer-id") int customerId,
			@PathVariable(name = "loan-number") int loanNumber) throws LoanServiceException {
		loanService.deleteLoan(customerId, loanNumber);
	}

	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLoans(@RequestBody List<LoanDTO> loans) {
		loanService.createLoans(loans);
	}

	@GetMapping("/properties")
	public ResponseEntity<String> getLoansProperties() throws JsonProcessingException {
		return new ResponseEntity<>(loanService.getLoansProperties(), HttpStatus.OK);
	}

}
