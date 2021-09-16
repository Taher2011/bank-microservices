package com.udemy.gatewayserver.customlfilter;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import reactor.core.publisher.Mono;

@Component
public class JWTTokenFilter extends AbstractGatewayFilterFactory<JWTTokenFilter.Config> {

	@Autowired
	private PropertyHolder propertyHolder;

	public JWTTokenFilter() {
		super(Config.class);
	}

	public static class Config {

	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			return chain.filter(exchange);
		};
	}

	@Bean
	public GlobalFilter postGlobalFilter() {
		return (exchange, chain) -> {
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				ServerHttpResponse response = exchange.getResponse();
				ServerHttpRequest request = exchange.getRequest();
				RequestPath path = request.getPath();
				if (ObjectUtils.equals("/v1/users/", path.toString())) {
					if (ObjectUtils.equals(response.getStatusCode(), HttpStatus.OK)) {
						String token = Jwts.builder()
								.setSubject(request.getHeaders().get("user").stream().findFirst().get())
								.setExpiration(new Date(System.currentTimeMillis()
										+ Long.parseLong(propertyHolder.getTokenExpiration())))
								.signWith(SignatureAlgorithm.HS512, propertyHolder.getTokenSecret()).compact();
						exchange.getResponse().getHeaders().add("token", token);
					} else {
						response = exchange.getResponse();
						response.setStatusCode(response.getStatusCode());
					}
				}
			}));
		};
	};

}
