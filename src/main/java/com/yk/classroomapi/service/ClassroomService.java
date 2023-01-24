package com.yk.classroomapi.service;

import com.yk.classroomapi.entity.Student;
import com.yk.classroomapi.entity.Teacher;
import com.yk.classroomapi.model.*;

import java.util.List;

public interface ClassroomService {

    List<Teacher> getAllTeachers();

    List<Student> getAllStudents();

    void register(RegisterRequest registerRequest);

    CommonStudentResponse getCommonStudents(List<String> teacherList);

    void suspendStudent(SuspendRequest suspendRequest);

    RetrieveNotificationResponse retrieveNotifications(RetrieveNotificationRequest retrieveNotificationRequest);
}
