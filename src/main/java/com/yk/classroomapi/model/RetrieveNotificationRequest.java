package com.yk.classroomapi.model;

import lombok.Data;

@Data
public class RetrieveNotificationRequest {

    private String teacher;

    private String notification;
}
