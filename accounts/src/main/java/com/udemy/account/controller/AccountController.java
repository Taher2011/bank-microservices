package com.udemy.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.udemy.account.exception.AccountServiceException;
import com.udemy.account.model.AccountDTO;
import com.udemy.account.model.CustomerDetailsDTO;
import com.udemy.account.service.AccountService;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@GetMapping("/customers")
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
	}

	@GetMapping("/customers/{customer-id}")
	public ResponseEntity<List<AccountDTO>> getAccountsForCustomer(@PathVariable(name = "customer-id") int customerId)
			throws AccountServiceException {
		return new ResponseEntity<>(accountService.getAccountsForCustomer(customerId), HttpStatus.OK);
	}

	@PostMapping("/customers/{customer-id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createAccount(@PathVariable(name = "customer-id") int customerId, @RequestBody AccountDTO account) {
		accountService.createAccount(customerId, account);
	}

	@DeleteMapping("/customers/{customer-id}/account/{account-number}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAccount(@PathVariable(name = "customer-id") int customerId,
			@PathVariable(name = "account-number") int accountNumber) throws AccountServiceException {
		accountService.deleteAccount(customerId, accountNumber);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public void createAccounts(@RequestBody List<AccountDTO> accounts) {
		accountService.createAccounts(accounts);
	}

	@GetMapping("/properties")
	public ResponseEntity<String> getAccountsProperties() throws JsonProcessingException {
		return new ResponseEntity<>(accountService.getAccountsProperties(), HttpStatus.OK);
	}

	@GetMapping("/customer-details/{customer-id}")
	public ResponseEntity<CustomerDetailsDTO> getCustomerDetails(
			@RequestHeader(required = false, name = "trace-id") String traceId,
			@PathVariable(name = "customer-id") int customerId) throws AccountServiceException {
		return new ResponseEntity<>(accountService.getCustomerDetails(traceId, customerId), HttpStatus.OK);
	}

}
