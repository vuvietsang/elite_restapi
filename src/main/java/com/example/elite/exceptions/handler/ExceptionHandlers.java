package com.example.elite.exceptions.handler;

import com.example.elite.dto.ResponseDto;
import com.example.elite.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.relation.RoleNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlers extends RuntimeException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(value = {UsernameOrPasswordNotFoundException.class, AuthenticationException.class})
    public ResponseEntity<Object> usernameOrPasswordNotFound(AuthenticationException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage("USER NAME OR PASSWORD IS INCORRECT");
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> usernameNotFoundException(UsernameNotFoundException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {RoleNotFoundException.class})
    public ResponseEntity<Object> roleNotFoundException(RoleNotFoundException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {UserNameExistException.class})
    public ResponseEntity<Object> userNameExistException(UserNameExistException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<Object> categoryNotFoundException(CategoryNotFoundException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {ProductNameExistException.class})
    public ResponseEntity<Object> productNameExistException(ProductNameExistException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> productNotFoundException(ProductNotFoundException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> notFoundException(NoSuchElementException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage("ID NOT FOUND!");
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {UserDisableException.class})
    public ResponseEntity<Object> notFoundException(UserDisableException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = {com.example.elite.exceptions.RoleNotFoundException.class})
    public ResponseEntity<Object> roleNotFoundException(com.example.elite.exceptions.RoleNotFoundException exception) {
        ResponseDto dto = new ResponseDto();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }


}
