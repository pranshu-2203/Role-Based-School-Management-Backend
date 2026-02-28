package com.School.Smart.Backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InviteException.class)
    public ResponseEntity<?>handleInviteException(InviteException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", "error","message", ex.getMessage()));
    }
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleAuthException(AuthorizationException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("status", "error",
                        "message", ex.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", "validation_error",
                        "errors", errors
                ));
    }
}
