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
     * Requirement did not mention notification requires at least 1 student email,
     * so we assume that a plain String is fine, and in this case retrievenotification
     * will take the default studentList from the specified teacher.
     */
    @NotEmpty(message = "cannot be null or empty")
    private String notification;
}
