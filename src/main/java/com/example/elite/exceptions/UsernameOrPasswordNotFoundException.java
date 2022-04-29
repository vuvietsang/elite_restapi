package com.example.elite.exceptions;

public class UsernameOrPasswordNotFoundException extends RuntimeException{
    public UsernameOrPasswordNotFoundException(String message) {
        super(message);
    }
}
