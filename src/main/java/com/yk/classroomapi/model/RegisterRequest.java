package com.yk.classroomapi.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class RegisterRequest {

    @NotEmpty(message = "cannot be null or empty")
    @Email(message = "must be a valid email")
    private String teacher;

    @NotEmpty(message = "input at least 1 student")
    private Set<
            @NotEmpty(message = "cannot be null or empty")
            @Email(message = "must be a valid email")
                    String> students;
}
