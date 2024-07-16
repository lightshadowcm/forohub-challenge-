package com.aluracursos.forohubchallenge.escudo.errores;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class HandlerCustomErrors {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity customErrorArgumentNotValid(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors().stream().map(DataValidationError::new).toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleSqlIntegritiConstraint(SQLIntegrityConstraintViolationException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IntegrityValidation.class)
    public ResponseEntity handleIntegrityValidation(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DataValidationError(String field, String error){
        public DataValidationError(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
