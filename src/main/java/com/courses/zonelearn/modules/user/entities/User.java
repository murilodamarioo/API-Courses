package com.courses.zonelearn.modules.user.entities;

import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.*;

@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;

    @Email(message = "Type a valid e-mail")
    private String email;
    private String password;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
}
