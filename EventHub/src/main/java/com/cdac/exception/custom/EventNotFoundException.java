package com.cdac.exception.custom;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String msg) { super(msg); }
}
