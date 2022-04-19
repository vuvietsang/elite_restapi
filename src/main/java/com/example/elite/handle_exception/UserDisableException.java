package com.example.elite.handle_exception;

public class UserDisableException extends RuntimeException{
    public UserDisableException(String message) {
        super(message);
    }
}
