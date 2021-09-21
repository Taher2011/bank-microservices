package com.udemy.gatewayserver.customlfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;

import reactor.core.publisher.Mono;

@Configuration
public class RequestResponseHeaderTraceFilter {

	private static final Logger logger = LoggerFactory.getLogger(RequestResponseHeaderTraceFilter.class);

	private static final String HEADER_TRACEID = "trace-id";

	private String traceId;

	@Order(1)
	@Bean
	public GlobalFilter addRequestResponseHeaderTraceId() {
		return (exchange, chain) -> {
			HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
			if (!ObjectUtils.isEmpty(requestHeaders.get(HEADER_TRACEID))) {
				logger.debug(HEADER_TRACEID + " {} ", requestHeaders.get(HEADER_TRACEID).stream().findFirst().get());
				return chain.filter(exchange);
			}
			traceId = java.util.UUID.randomUUID().toString();
			logger.debug(HEADER_TRACEID + " {} ", traceId);
			exchange.mutate().request(exchange.getRequest().mutate().header(HEADER_TRACEID, traceId).build()).build();
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				HttpHeaders responseHeaders = exchange.getRequest().getHeaders();
				traceId = responseHeaders.get(HEADER_TRACEID).stream().findFirst().get();
				logger.debug(HEADER_TRACEID + " {} ", traceId);
				exchange.getResponse().getHeaders().add(HEADER_TRACEID, traceId);
			}));
		};
	}

}
