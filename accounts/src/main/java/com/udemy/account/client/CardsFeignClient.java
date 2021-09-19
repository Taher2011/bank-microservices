package com.udemy.account.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.udemy.account.config.BasicAuthForCardFeignClient;
import com.udemy.account.model.CardDTO;

@FeignClient(name = "cards", configuration = BasicAuthForCardFeignClient.class)
public interface CardsFeignClient {

	@GetMapping(path = "v1/cards/customers/{customer-id}", consumes = "application/json")
	List<CardDTO> getCardsForCustomer(@RequestHeader("trace-id") String traceId,
			@PathVariable(name = "customer-id") int customerId);

}
