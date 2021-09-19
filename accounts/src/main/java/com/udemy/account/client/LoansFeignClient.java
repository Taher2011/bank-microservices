package com.udemy.account.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.udemy.account.config.BasicAuthForLoanFeignClient;
import com.udemy.account.model.LoanDTO;

@FeignClient(name = "loans", configuration = BasicAuthForLoanFeignClient.class)
public interface LoansFeignClient {

	@GetMapping(path = "v1/loans/customers/{customer-id}", consumes = "application/json")
	List<LoanDTO> getLoansForCustomer(@RequestHeader("trace-id") String traceId,
			@PathVariable(name = "customer-id") int customerId);

}
