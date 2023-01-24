package com.yk.classroomapi.service.impl;

import com.yk.classroomapi.entity.Student;
import com.yk.classroomapi.entity.Teacher;
import com.yk.classroomapi.exception.ResourceNotFoundException;
import com.yk.classroomapi.model.*;
import com.yk.classroomapi.repository.StudentRepository;
import com.yk.classroomapi.repository.TeacherRepository;
import com.yk.classroomapi.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void register(RegisterRequest registerRequest) {

        Teacher teacher = teacherRepository.findByEmail(registerRequest.getTeacher())
                .orElse(new Teacher(registerRequest.getTeacher()));

        /**
         * To remove existing student object first, else will throw duplicate error
         */
        Set<Student> newStudentsToRegister = convertStudentsToObjSet(registerRequest.getStudents());
        Set<String> existingStudents = convertStudentsToStrSet(teacher.getStudents());
        newStudentsToRegister.removeIf(s -> existingStudents.contains(s.getEmail()));

        teacher.addStudents(newStudentsToRegister);

        teacherRepository.save(teacher);
    }

    @Override
    public CommonStudentResponse getCommonStudents(List<String> teacherList) {

        if (teacherList.size() == 1) {
            Teacher teacher = teacherRepository.findByEmail(teacherList.get(0))
                    .orElseThrow(() -> new ResourceNotFoundException("teacher", teacherList.get(0)));
            return new CommonStudentResponse(convertStudentsToStrSet(teacher.getStudents()));

        }

        /**
         * To find common students between 2 or more teachers,
         * compile all students in a list first, then return only the duplicates
         */
        List<String> studentList = new ArrayList<>();
        teacherList.forEach(t -> {
            Teacher teacher = teacherRepository.findByEmail(t)
                    .orElseThrow(() -> new ResourceNotFoundException("teacher", t));
            studentList.addAll(new ArrayList<>(convertStudentsToStrSet(teacher.getStudents())));
        });

        Set<String> duplicatesCheck = new HashSet<>();

        return new CommonStudentResponse(studentList.stream()
                .filter(s -> !duplicatesCheck.add(s)).collect(Collectors.toSet()));
    }

    @Override
    public void suspendStudent(SuspendRequest suspendRequest) {

        Student student = studentRepository.findByEmail(suspendRequest.getStudent())
                .orElseThrow(() -> new ResourceNotFoundException("student", suspendRequest.getStudent()));

        student.setSuspended(true);
        studentRepository.save(student);
    }

    /**
     * Will only throw an error if teacher is not found in database.
     * For students in the notification String, a student not found in database will still show as success as the
     * requirement did not mention that student not assigned under any teacher == student does not exist.
     * However, there is no way to check if this student is suspended or not, so assumption here is to omit the
     * result if student specified in notification string is not found in database.
     *
     * @param retrieveNotificationRequest
     * @return
     */
    @Override
    public RetrieveNotificationResponse retrieveNotifications(RetrieveNotificationRequest retrieveNotificationRequest) {

        Teacher teacher = teacherRepository.findByEmail(retrieveNotificationRequest.getTeacher())
                .orElseThrow(() -> new ResourceNotFoundException("teacher", retrieveNotificationRequest.getTeacher()));

        Set<String> studentsByTeacher = convertStudentsToStrSet(teacher.getStudents());

        Set<String> studentsInNotification = Stream.of(retrieveNotificationRequest.getNotification().split(" @"))
                .skip(1).collect(Collectors.toSet());

        System.out.println("students in notification: " + studentsInNotification);

        Set<String> studentsInNotificationNotSuspended = getAllStudents().stream()
                .filter(s -> studentsInNotification.contains(s.getEmail()))
                .filter(s -> !s.isSuspended())
                .map(s -> s.getEmail())
                .collect(Collectors.toSet());

        Set<String> studentsByTeacherNotSuspended = teacher.getStudents().stream()
                .filter(s -> !s.isSuspended())
                .map(s -> s.getEmail())
                .collect(Collectors.toSet());

        Set<String> studentsToReceiveNotification = new HashSet<>();
        studentsToReceiveNotification.addAll(studentsInNotificationNotSuspended);
        studentsToReceiveNotification.addAll(studentsByTeacherNotSuspended);

        return new RetrieveNotificationResponse(studentsToReceiveNotification);
    }

    private Set<Student> convertStudentsToObjSet(Set<String> students) {

        Set<Student> setOfStudents = new HashSet<>();
        students.forEach(student -> setOfStudents.add(new Student(student)));

        return setOfStudents;
    }

    private Set<String> convertStudentsToStrSet(Set<Student> students) {

        return students.stream().map(s -> s.getEmail()).collect(Collectors.toSet());
    }
}
