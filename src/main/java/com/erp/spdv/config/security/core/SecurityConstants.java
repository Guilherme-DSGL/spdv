package com.erp.spdv.config.security.core;

public class SecurityConstants {
	// default secret
	public static final String SECRET_KEY = "secret";
	public static final long EXPIRATION_TIME = 900_000; // 15 mins
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/api/users";
	public static final Integer ID_SUBSTRING_AUTH_HEADER = 7;

}
