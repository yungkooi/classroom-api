package com.yk.classroomapi.controller;

import com.yk.classroomapi.entity.Student;
import com.yk.classroomapi.entity.Teacher;
import com.yk.classroomapi.model.*;
import com.yk.classroomapi.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return new ResponseEntity<>(classroomService.getAllTeachers(), HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(classroomService.getAllStudents(), HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        classroomService.register(registerRequest);
    }

    @GetMapping("/commonstudents")
    public ResponseEntity<CommonStudentResponse> getCommonStudents(
            @RequestParam(value = "teacher")
            List<@Email(message = "must be a valid email") String> teacherList){
        return new ResponseEntity<>(classroomService.getCommonStudents(teacherList), HttpStatus.OK);
    }

    @PostMapping("/suspend")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void suspendStudent(@Valid @RequestBody SuspendRequest suspendRequest) {
        classroomService.suspendStudent(suspendRequest);
    }

    @PostMapping("/retrievefornotifications")
    public ResponseEntity<RetrieveNotificationResponse> retrieveNotifications(
            @Valid @RequestBody RetrieveNotificationRequest retrieveNotificationRequest) {
        return new ResponseEntity<>(classroomService.retrieveNotifications(retrieveNotificationRequest), HttpStatus.OK);
    }
}
