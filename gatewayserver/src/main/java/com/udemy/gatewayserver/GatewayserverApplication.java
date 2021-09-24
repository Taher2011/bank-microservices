package com.udemy.gatewayserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.udemy.gatewayserver.customlfilter.AuthorizationHeaderFilter;
import com.udemy.gatewayserver.customlfilter.JWTTokenFilter;
import com.udemy.gatewayserver.customlfilter.PropertyHolder;

@SpringBootApplication
@EnableEurekaClient
public class GatewayserverApplication {

	private static final String BEARER = "Bearer (.*)";

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public PropertyHolder holder() {
		PropertyHolder holder = new PropertyHolder();
		holder.setTokenSecret(environment.getProperty("token.secret"));
		holder.setTokenExpiration(environment.getProperty("token.expiration"));
		holder.setTokenSubject(environment.getProperty("token.subject"));
		holder.setAccountSecurityPassword(environment.getProperty("account.security.password"));
		holder.setCardSecurityPassword(environment.getProperty("card.security.password"));
		holder.setLoanSecurityPassword(environment.getProperty("loan.security.password"));
		return holder;
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {

		return builder.routes()

				.route(p -> p.path("/bank/users/token/**").and().method(HttpMethod.POST)
						.filters(f -> f.rewritePath("/bank/users/token/?(?<remaining>.*)", "/${remaining}")
								.filter(new JWTTokenFilter().apply(new JWTTokenFilter.Config())))
						.uri("lb://USERS"))

				.route(p -> p.path("/bank/accounts/**").and()
						.header(HttpHeaders.AUTHORIZATION, BEARER)
						.filters(f -> f.rewritePath("/bank/accounts/?(?<remaining>.*)", "/${remaining}").filter(
								new AuthorizationHeaderFilter(holder()).apply(new AuthorizationHeaderFilter.Config())))
						.uri("lb://ACCOUNTS"))

				.route(p -> p.path("/bank/loans/**").and()
						.header(HttpHeaders.AUTHORIZATION, BEARER)
						.filters(f -> f.rewritePath("/bank/loans/?(?<remaining>.*)", "/${remaining}").filter(
								new AuthorizationHeaderFilter(holder()).apply(new AuthorizationHeaderFilter.Config())))
						.uri("lb://LOANS"))

				.route(p -> p.path("/bank/cards/**").and()
						.header(HttpHeaders.AUTHORIZATION, BEARER)
						.filters(f -> f.rewritePath("/bank/cards/?(?<remaining>.*)", "/${remaining}").filter(
								new AuthorizationHeaderFilter(holder()).apply(new AuthorizationHeaderFilter.Config())))
						.uri("lb://CARDS"))

				.build();

	}

}
