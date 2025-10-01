package com.jeferson.msvc.users.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeExeption(RuntimeException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
