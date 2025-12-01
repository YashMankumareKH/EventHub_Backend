package com.cdac.exception.custom;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String msg) {
        super(msg);
    }
}

