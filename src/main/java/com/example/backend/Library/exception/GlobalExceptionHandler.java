package com.example.backend.Library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionHandles.ValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationException(ExceptionHandles.ValidationException ex) {
        Map<String, List<String>> responseBody = new HashMap<>();
        responseBody.put("errors", ex.getErrors());
        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler(ExceptionHandles.ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ExceptionHandles.ResourceNotFoundException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }
}