package com.udemy.loan.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.udemy.loan.config.LoanServiceConfig;
import com.udemy.loan.entity.Loan;
import com.udemy.loan.exception.ErrorCode;
import com.udemy.loan.exception.LoanServiceException;
import com.udemy.loan.model.LoanDTO;
import com.udemy.loan.model.Property;
import com.udemy.loan.repository.LoanRepository;

@Service
public class LoanService {

	private static Logger logger = LoggerFactory.getLogger(LoanService.class);

	private final LoanRepository loanRepository;

	private final LoanServiceConfig loanServiceConfig;

	private final Random random;

	public LoanService(LoanRepository loanRepository, LoanServiceConfig loanServiceConfig, Random random) {
		super();
		this.loanRepository = loanRepository;
		this.loanServiceConfig = loanServiceConfig;
		this.random = random;
	}

	public List<LoanDTO> getLoansForCustomer(String traceId, int customerId) throws LoanServiceException {
		logger.debug("trace-id is {} ", traceId);
		logger.info("started getting customer loan details for customer-id {} ", customerId);
		List<Loan> loans = loanRepository.findByCustomerIdOrderByStartDate(customerId);
		List<LoanDTO> loansDTO = null;
		if (ObjectUtils.isEmpty(loans)) {
			logger.error("customer-id {} not found", customerId);
			throw new LoanServiceException(ErrorCode.CUSTOMER_NOT_FOUND,
					String.format(ErrorCode.CUSTOMER_NOT_FOUND.getMessage(), customerId));
		}
		loansDTO = loans.stream().map(e -> {
			LoanDTO loanDTO = new LoanDTO();
			loanDTO.setCustomerId(e.getCustomerId());
			loanDTO.setLoanId(e.getLoanId());
			loanDTO.setStartDate(e.getStartDate());
			loanDTO.setLoanType(e.getLoanType());
			loanDTO.setTotalLoan(e.getTotalLoan());
			loanDTO.setAmountPaid(e.getAmountPaid());
			loanDTO.setOutstandingAmount(e.getOutstandingAmount());
			loanDTO.setCreateDate(e.getCreateDate());
			return loanDTO;
		}).collect(Collectors.toList());
		logger.info("completed getting customer loan details for customer-id {} ", customerId);
		return loansDTO;
	}

	public void createLoan(int customerId, LoanDTO loanDTO) {
		logger.info("started creating customer loan details for customer-id {} ", customerId);
		Loan loan = new Loan();
		loan.setCustomerId(customerId);
		loan.setLoanId(random.nextInt(1000));
		loan.setLoanType(loanDTO.getLoanType());
		loan.setAmountPaid(loanDTO.getAmountPaid());
		loan.setCreateDate(loanDTO.getCreateDate());
		loan.setOutstandingAmount(loanDTO.getOutstandingAmount());
		loan.setStartDate(loanDTO.getStartDate());
		loan.setTotalLoan(loanDTO.getTotalLoan());
		loanRepository.save(loan);
		logger.info("completed creating customer loan details for customer-id {} ", customerId);
	}

	public void deleteLoan(int customerId, int loanNumber) throws LoanServiceException {
		logger.info("started checking details for customer-id {} and loan-number {} ", customerId, loanNumber);
		Optional<Loan> card = loanRepository.findByCustomerIdAndLoanId(customerId, loanNumber);
		logger.info("completed checking details for customer-id {} and loan-number {} ", customerId, loanNumber);
		if (!card.isPresent()) {
			logger.error("customer with customer-id {} and loan-number {} not found", customerId, loanNumber);
			throw new LoanServiceException(ErrorCode.CUSTOMER_WITH_LOAN_NUMBER_NOT_FOUND,
					String.format(ErrorCode.CUSTOMER_WITH_LOAN_NUMBER_NOT_FOUND.getMessage(), customerId, loanNumber));
		}
		logger.info("started deleting loan-number {} ", loanNumber);
		loanRepository.deleteById(loanNumber);
		logger.info("completed deleting loan-number {} ", loanNumber);

	}

	public void createLoans(List<LoanDTO> loanDTOs) {
		logger.info("started creating customer loan details for customers");
		List<Loan> loans = loanDTOs.stream().map(e -> {
			Loan loan = new Loan();
			loan.setCustomerId(e.getCustomerId());
			loan.setLoanId(random.nextInt(1000));
			loan.setLoanType(e.getLoanType());
			loan.setAmountPaid(e.getAmountPaid());
			loan.setCreateDate(e.getCreateDate());
			loan.setOutstandingAmount(e.getOutstandingAmount());
			loan.setStartDate(e.getStartDate());
			loan.setTotalLoan(e.getTotalLoan());
			return loan;
		}).collect(Collectors.toList());
		logger.info("completed creating customer loan details for customers");
		loanRepository.saveAll(loans);
	}

	public List<LoanDTO> getLoans() {
		logger.info("started getting customer loan details");
		List<Loan> loans = loanRepository.findAll();
		if (ObjectUtils.isNotEmpty(loans)) {
			List<LoanDTO> loanDTOs = new ArrayList<>();
			loans.stream().forEach(e -> {
				LoanDTO loanDTO = new LoanDTO();
				loanDTO.setCustomerId(e.getCustomerId());
				loanDTO.setLoanId(e.getLoanId());
				loanDTO.setLoanType(e.getLoanType());
				loanDTO.setAmountPaid(e.getAmountPaid());
				loanDTO.setCreateDate(e.getCreateDate());
				loanDTO.setOutstandingAmount(e.getOutstandingAmount());
				loanDTO.setStartDate(e.getStartDate());
				loanDTO.setTotalLoan(e.getTotalLoan());
				loanDTOs.add(loanDTO);
			});
			logger.info("completed getting customer loan details");
			return loanDTOs;
		}
		logger.info("completed getting customer loan details");
		return Collections.emptyList();
	}

	public String getLoansProperties() throws JsonProcessingException {
		logger.info("started getting loan properties");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Property property = Property.builder().msg(loanServiceConfig.getMsg())
				.buildVersion(loanServiceConfig.getBuildVersion()).mailDetails(loanServiceConfig.getMailDetails())
				.activeBranches(loanServiceConfig.getActiveBranches()).build();
		logger.info("completed getting loan properties");
		return ow.writeValueAsString(property);
	}
}
