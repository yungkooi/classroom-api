package com.yk.classroomapi.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "teacher_students",
        joinColumns = { @JoinColumn(name = "teacher_email")},
        inverseJoinColumns = { @JoinColumn(name = "student_email")})
    private Set<Student> students = new HashSet<>();

    public Teacher() {

    }

    public Teacher(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.getTeachers().add(this);
    }

    public void addStudents(Set<Student> students) {
        this.students.addAll(students);
    }

    public void removeStudent(String studentEmail) {
        Student student = this.students.stream()
                .filter(t -> studentEmail.equalsIgnoreCase(t.getEmail())).findFirst().orElse(null);
        if (student != null) {
            this.students.remove(student);
            student.getTeachers().remove(this);
        }
    }
}
