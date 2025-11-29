package com.cdac.custom_exception;

public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String mesg) {
		super(mesg);
	}
	
}
