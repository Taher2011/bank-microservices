//package com.udemy.gatewayserver.globalfilter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.server.ServerWebExchange;
//
//import reactor.core.publisher.Mono;
//
//@Order(1)
//@Component
//public class RequestTraceFilter implements GlobalFilter {
//
//	private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);
//
//	public static final String CORRELATION_ID = "correlation-id";
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
//		if (!isCorrelationIdPresent(requestHeaders)) {
//			logger.debug("correlation-id found in headers: {}. ",
//					requestHeaders.get(CORRELATION_ID).stream().findFirst().get());
//			return chain.filter(exchange);
//		}
//		String correlationID = generateCorrelationId();
//		exchange = setCorrelationId(exchange, correlationID);
//		logger.debug("correlation-id generated in headers: {}.", correlationID);
//		return chain.filter(exchange);
//	}
//
//	private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
//		return ObjectUtils.isEmpty(requestHeaders.get(CORRELATION_ID));
//	}
//
//	private String generateCorrelationId() {
//		return java.util.UUID.randomUUID().toString();
//	}
//
//	public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
//		return exchange.mutate().request(exchange.getRequest().mutate().header(CORRELATION_ID, correlationId).build())
//				.build();
//	}
//
//}
