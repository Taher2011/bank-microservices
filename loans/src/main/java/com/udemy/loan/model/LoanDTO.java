package com.udemy.loan.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoanDTO {

	@JsonProperty("loan-id")
	private Integer loanId;

	@JsonProperty("customer-id")
	private Integer customerId;

	@JsonProperty("start-date")
	private LocalDate startDate;

	@JsonProperty("loan-type")
	private String loanType;

	@JsonProperty("total-loan")
	private int totalLoan;

	@JsonProperty("amount-paid")
	private int amountPaid;

	@JsonProperty("outstanding-amount")
	private int outstandingAmount;

	@JsonProperty("created-date")
	private LocalDate createDate;

}
