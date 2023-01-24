package com.yk.classroomapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "email")
    private String email;

    @ColumnDefault("false")
    @Column(name = "suspended")
    private boolean suspended;

    public Student(String email) {
        this.email = email;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },
    mappedBy = "students")
    @JsonIgnore
    private Set<Teacher> teachers = new HashSet<>();

}
