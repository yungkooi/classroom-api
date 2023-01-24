package com.yk.classroomapi.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RetrieveNotificationRequest {

    @NotEmpty(message = "cannot be null or empty")
    @Email(message = "must be a valid email")
    private String teacher;

    /**
     * Add @Pattern and provide regex for a more refined validation.
     */
    @NotEmpty(message = "cannot be null or empty")
    private String notification;
}
