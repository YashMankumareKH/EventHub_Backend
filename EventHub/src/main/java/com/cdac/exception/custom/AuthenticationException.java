package com.cdac.exception.custom;

public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String mesg) {
		super(mesg);
	}
	
}
