package com.yk.classroomapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CommonStudentResponse {

    private Set<String> students;
}
