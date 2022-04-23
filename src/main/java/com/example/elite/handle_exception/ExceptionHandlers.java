package com.example.elite.handle_exception;

import com.example.elite.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.relation.RoleNotFoundException;
import java.lang.reflect.InvocationTargetException;
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
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage("USER NAME OR PASSWORD IS INCORRECT");
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> usernameNotFoundException(UsernameNotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {RoleNotFoundException.class})
    public ResponseEntity<Object> roleNotFoundException(RoleNotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {UserNameExistException.class})
    public ResponseEntity<Object> userNameExistException(UserNameExistException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<Object> categoryNotFoundException(CategoryNotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {ProductNameExistException.class})
    public ResponseEntity<Object> productNameExistException(ProductNameExistException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> productNotFoundException(ProductNotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> notFoundException(NoSuchElementException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage("ID NOT FOUND!");
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {UserDisableException.class})
    public ResponseEntity<Object> notFoundException(UserDisableException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = {com.example.elite.handle_exception.RoleNotFoundException.class})
    public ResponseEntity<Object> roleNotFoundException(com.example.elite.handle_exception.RoleNotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }


}
