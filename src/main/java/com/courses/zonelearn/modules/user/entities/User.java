package com.courses.zonelearn.modules.user.entities;

import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
}
