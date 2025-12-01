package com.cdac.exception.custom;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String msg) {
        super(msg);
    }
}
