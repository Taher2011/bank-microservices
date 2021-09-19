package com.udemy.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class BasicAuthForCardFeignClient {

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptorForCard() {
		return new BasicAuthRequestInterceptor("card", "card@1234");
	}

}
