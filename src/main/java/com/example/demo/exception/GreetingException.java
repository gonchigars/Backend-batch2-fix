package com.example.demo.exception;

public class GreetingException extends RuntimeException {
    public GreetingException(String message) {
        super(message);
    }
}