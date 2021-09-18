package com.udemy.account.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.udemy.account.client.CardsFeignClient;
import com.udemy.account.client.LoansFeignClient;
import com.udemy.account.config.AccountServiceConfig;
import com.udemy.account.entity.Account;
import com.udemy.account.exception.AccountServiceException;
import com.udemy.account.exception.ErrorCode;
import com.udemy.account.model.AccountDTO;
import com.udemy.account.model.CardDTO;
import com.udemy.account.model.CustomerDetailsDTO;
import com.udemy.account.model.LoanDTO;
import com.udemy.account.model.Property;
import com.udemy.account.repository.AccountRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	private final AccountRepository accountRepository;

	private final AccountServiceConfig accountServiceConfig;

	@Autowired
	private CardsFeignClient cardsFeignClient;

	@Autowired
	private LoansFeignClient loansFeignClient;

	public AccountService(AccountRepository accountRepository, AccountServiceConfig accountServiceConfig) {
		super();
		this.accountRepository = accountRepository;
		this.accountServiceConfig = accountServiceConfig;
	}

	public List<AccountDTO> getAccountsForCustomer(int customerId) throws AccountServiceException {
		logger.info("started getting customer account details for customerId {} ", customerId);
		List<Account> accounts = accountRepository.findByCustomerId(customerId);
		List<AccountDTO> accountsDTO = null;
		if (!ObjectUtils.isEmpty(accounts)) {
			accountsDTO = accounts.stream().map(e -> {
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setCustomerId(e.getCustomerId());
				accountDTO.setAccountNumber(e.getAccountNumber());
				accountDTO.setAccountType(e.getAccountType());
				accountDTO.setBranchAddress(e.getBranchAddress());
				accountDTO.setCreateDate(e.getCreateDate());
				return accountDTO;
			}).collect(Collectors.toList());
			logger.info("completed getting customer account details for customerId {} ", customerId);
			return accountsDTO;
		} else {
			logger.error("Customer id {} not found", customerId);
			throw new AccountServiceException(ErrorCode.CUSTOMER_NOT_FOUND);
		}

	}

	public void createAccount(int customerId, AccountDTO accountDTO) {
		logger.info("started creating customer account details for customerId {} ", accountDTO.getCustomerId());
		Account account = new Account();
		int accountNumber = ThreadLocalRandom.current().nextInt();
		account.setAccountNumber(accountNumber);
		account.setCustomerId(customerId);
		account.setAccountType(accountDTO.getAccountType());
		account.setBranchAddress(accountDTO.getBranchAddress());
		account.setCreateDate(accountDTO.getCreateDate());
		logger.info("completed creating customer account details for customerId {} ", accountDTO.getCustomerId());
		accountRepository.save(account);
	}

	public void createAccounts(List<AccountDTO> accountDTOs) {
		logger.info("started creating customer account details for customers");
		List<Account> accounts = accountDTOs.stream().map(e -> {
			Account account = new Account();
			account.setCustomerId(e.getCustomerId());
			int accountNumber = ThreadLocalRandom.current().nextInt();
			account.setAccountNumber(accountNumber);
			account.setAccountType(e.getAccountType());
			account.setBranchAddress(e.getBranchAddress());
			account.setCreateDate(e.getCreateDate());
			return account;
		}).collect(Collectors.toList());
		logger.info("completed creating customer account details for customerId");
		accountRepository.saveAll(accounts);
	}

	public List<AccountDTO> getAccounts() {
		logger.info("started getting customer account details");
		List<Account> accounts = accountRepository.findAll();
		if (ObjectUtils.isNotEmpty(accounts)) {
			List<AccountDTO> accountDTOs = new ArrayList<>();
			accounts.stream().forEach(e -> {
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setCustomerId(e.getCustomerId());
				accountDTO.setAccountNumber(e.getAccountNumber());
				accountDTO.setAccountType(e.getAccountType());
				accountDTO.setBranchAddress(e.getBranchAddress());
				accountDTO.setCreateDate(e.getCreateDate());
				accountDTOs.add(accountDTO);
			});
			logger.info("completed getting customer account details");
			return accountDTOs;
		}
		logger.info("completed getting customer account details");
		return Collections.emptyList();
	}

	public String getAccountsProperties() throws JsonProcessingException {
		logger.info("started getting account properties");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Property property = Property.builder().msg(accountServiceConfig.getMsg())
				.buildVersion(accountServiceConfig.getBuildVersion()).mailDetails(accountServiceConfig.getMailDetails())
				.activeBranches(accountServiceConfig.getActiveBranches()).build();
		logger.info("completed getting account properties");
		return ow.writeValueAsString(property);
	}

	@CircuitBreaker(name = "customerDetails", fallbackMethod = "customerDetailsFallBack")
//	@Retry(name = "retyrCustomerDetails", fallbackMethod = "customerDetailsFallBack")
	public CustomerDetailsDTO getCustomerDetails(String traceId, int customerId) throws AccountServiceException {
		logger.debug("traceId is {} ", traceId);
		logger.info("started getting customer account details for customerId {} ", customerId);
		List<AccountDTO> accountDetails = getAccountsForCustomer(customerId);
		List<LoanDTO> loanDetails = loansFeignClient.getLoansForCustomer(traceId, customerId);
		List<CardDTO> cardDetails = cardsFeignClient.getCardsForCustomer(traceId, customerId);
		CustomerDetailsDTO customerDetails = new CustomerDetailsDTO();
		customerDetails.setAccounts(accountDetails);
		customerDetails.setLoans(loanDetails);
		customerDetails.setCards(cardDetails);
		logger.info("completed getting customer account details for customerId {} ", customerId);
		return customerDetails;
	}

	private CustomerDetailsDTO customerDetailsFallBack(String traceId, int customerId, Throwable t)
			throws AccountServiceException {
		logger.debug("traceId is {} ", traceId);
		logger.info("started getting customer account details for customerId {} via fallback mechanism", customerId);
		List<AccountDTO> accountDetails = getAccountsForCustomer(customerId);
		List<LoanDTO> loanDetails = loansFeignClient.getLoansForCustomer(traceId, customerId);
		CustomerDetailsDTO customerDetails = new CustomerDetailsDTO();
		customerDetails.setAccounts(accountDetails);
		customerDetails.setLoans(loanDetails);
		logger.info("completed getting customer account details for customerId {} via fallback mechanism", customerId);
		return customerDetails;
	}
}
