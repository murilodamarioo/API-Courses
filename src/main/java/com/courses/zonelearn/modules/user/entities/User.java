package com.courses.zonelearn.modules.user.entities;

import com.courses.zonelearn.modules.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.UUID;

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

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
}
