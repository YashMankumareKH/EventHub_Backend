package com.cdac.exception.custom;

public class RegistrationNotFoundException extends ResourceNotFoundException {
    public RegistrationNotFoundException(String message) {
        super(message);
    }
}
