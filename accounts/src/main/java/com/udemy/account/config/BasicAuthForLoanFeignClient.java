package com.udemy.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class BasicAuthForLoanFeignClient {

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptorForLoan() {
		return new BasicAuthRequestInterceptor("loan", "loan@1234");
	}

}
