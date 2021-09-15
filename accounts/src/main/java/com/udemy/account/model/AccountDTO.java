package com.udemy.account.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AccountDTO {

	@JsonProperty("customer-id")
	private Integer customerId;

	@JsonProperty("account-number")
	private Integer accountNumber;

	@JsonProperty("account-type")
	private String accountType;

	@JsonProperty("branch-address")
	private String branchAddress;

	@JsonProperty("created-date")
	private LocalDate createDate;

}
