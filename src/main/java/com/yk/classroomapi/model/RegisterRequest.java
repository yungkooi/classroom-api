package com.yk.classroomapi.model;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {


    private String teacher;

    private Set<String> students;
}
