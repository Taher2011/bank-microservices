package com.udemy.account.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.udemy.account.model.CardDTO;

@FeignClient("cards")
public interface CardsFeignClient {

	@GetMapping(path = "v1/cards/{customer-id}", consumes = "application/json")
	List<CardDTO> getCardsForCustomer(@RequestHeader("bank-correlation-id") String correlationid,
			@PathVariable(name = "customer-id") int customerId);

}
