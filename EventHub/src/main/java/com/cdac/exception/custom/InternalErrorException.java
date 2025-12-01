package com.cdac.exception.custom;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String msg) {
        super(msg);
    }
}
