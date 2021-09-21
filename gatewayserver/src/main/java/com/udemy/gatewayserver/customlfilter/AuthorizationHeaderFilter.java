package com.udemy.gatewayserver.customlfilter;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Configuration
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	private PropertyHolder propertyHolder;

	public AuthorizationHeaderFilter(PropertyHolder propertyHolder) {
		super(Config.class);
		this.propertyHolder = propertyHolder;
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
			}
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).stream().findFirst().get();
			String jwt = authorizationHeader.replace("Bearer", "");
			if (!isJwtValid(jwt)) {
				return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private boolean isJwtValid(String jwt) {
		boolean isValid = true;
		try {
			String subject = Jwts.parser().setSigningKey(propertyHolder.getTokenSecret()).parseClaimsJws(jwt).getBody()
					.getSubject();
			if (ObjectUtils.notEqual(subject, propertyHolder.getTokenSubject())) {
				isValid = false;
				return isValid;
			}
		} catch (Exception e) {
			isValid = false;
			return isValid;
		}

		return isValid;
	}

}
