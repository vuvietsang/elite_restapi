package com.example.elite.handle_exception;

public class UsernameOrPasswordNotFoundException extends RuntimeException{
    public UsernameOrPasswordNotFoundException(String message) {
        super(message);
    }
}
