package com.example.elite.handle_exception;

public class ProductNameExistException extends RuntimeException{
    public ProductNameExistException(String message) {
        super(message);
    }
}
