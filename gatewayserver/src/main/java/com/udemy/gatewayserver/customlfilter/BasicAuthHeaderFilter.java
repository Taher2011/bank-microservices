package com.udemy.gatewayserver.customlfilter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Configuration
public class BasicAuthHeaderFilter {

	private static final Logger logger = LoggerFactory.getLogger(BasicAuthHeaderFilter.class);

	private static final String BASIC = "Basic ";

	@Autowired
	private PropertyHolder propertyHolder;

	@Order(2)
	@Bean
	public GlobalFilter addBasicAuthenticationHeader() {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			if (StringUtils.contains(request.getPath().toString(), "/v1/accounts/")) {
				exchange.mutate().request(exchange.getRequest().mutate()
						.header(HttpHeaders.AUTHORIZATION, BASIC + propertyHolder.getAccountSecurityPassword()).build())
						.build();
			} else if (StringUtils.contains(request.getPath().toString(), "/v1/loans/")) {
				exchange.mutate().request(exchange.getRequest().mutate()
						.header(HttpHeaders.AUTHORIZATION, BASIC + propertyHolder.getLoanSecurityPassword()).build())
						.build();
			} else if (StringUtils.contains(request.getPath().toString(), "/v1/cards/")) {
				exchange.mutate().request(exchange.getRequest().mutate()
						.header(HttpHeaders.AUTHORIZATION, BASIC + propertyHolder.getCardSecurityPassword()).build())
						.build();
			}
			return chain.filter(exchange);
		};
	}

}
