package com.yk.classroomapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles bean validations with fieldName and errorMessage
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Map<String, String>> handleBeanValidationError(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic/uncaught errors that result in RuntimeException
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<Map<String, String>> handleGenericException(RuntimeException ex) {

        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.I_AM_A_TEAPOT);
    }


}
