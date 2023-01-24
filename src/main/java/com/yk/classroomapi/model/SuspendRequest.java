package com.yk.classroomapi.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class SuspendRequest {


    @NotEmpty(message = "cannot be null or empty")
    @Email(message = "must be a valid email")
    private String student;
}
