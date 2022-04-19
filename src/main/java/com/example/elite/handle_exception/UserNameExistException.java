package com.example.elite.handle_exception;

public class UserNameExistException extends RuntimeException{
    public UserNameExistException(String message) {
        super(message);
    }
}
