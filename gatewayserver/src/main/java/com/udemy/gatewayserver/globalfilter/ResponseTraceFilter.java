//package com.udemy.gatewayserver.globalfilter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class ResponseTraceFilter {
//
//	private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);
//
//	public static final String CORRELATION_ID = "correlation-id";
//
//	@Bean
//	public GlobalFilter postGlobalFilter() {
//		return (exchange, chain) -> {
//			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//				HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
//				String correlationId = requestHeaders.get(CORRELATION_ID).stream().findFirst().get();
//				logger.debug("updated the correlation id to the outbound headers. {}", correlationId);
//				exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
//			}));
//		};
//	};
//
//}
