package com.example.elite.exceptions;

public class ProductNameExistException extends RuntimeException{
    public ProductNameExistException(String message) {
        super(message);
    }
}
