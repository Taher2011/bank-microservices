package com.udemy.gatewayserver.customlfilter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropertyHolder {

	public String tokenSecret;

	public String tokenExpiration;

	public String tokenSubject;
	
	public String authorizationHeaderAccount;

	public String authorizationHeaderLoan;

	public String authorizationHeaderCard;

}
