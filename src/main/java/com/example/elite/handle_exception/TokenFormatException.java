package com.example.elite.handle_exception;

public class TokenFormatException extends RuntimeException{
    public TokenFormatException(String message) {
        super(message);
    }
}
