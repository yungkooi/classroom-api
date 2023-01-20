package com.yk.classroomapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String fieldName, Object fieldValue) {
        super(String.format("%s %s not found in database!", fieldName, fieldValue));
    }
}
