package com.udemy.card.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDTO {

	@JsonProperty("card-id")
	private int cardId;

	@JsonProperty("amount-used")
	private int amountUsed;

	@JsonProperty("avilable-amount")
	private int avilableAmount;

	@JsonProperty("card-number")
	private String cardNumber;

	@JsonProperty("card-type")
	private String cardType;

	@JsonProperty("create-date")
	private LocalDate createDate;

	@JsonProperty("customer-id")
	private int customerId;

	@JsonProperty("total-limit")
	private int totalLimit;

}
