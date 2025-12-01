package com.cdac.exception.custom;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String msg) {
        super(msg);
    }
}

