package com.udemy.account.model;

import java.util.List;

import lombok.Data;

@Data
public class CustomerDetailsDTO {

	private List<AccountDTO> accounts;

	private List<LoanDTO> loans;
	
	private List<CardDTO> cards;

}
