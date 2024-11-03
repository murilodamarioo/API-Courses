package com.courses.zonelearn.modules.user.entities;

import com.courses.zonelearn.modules.user.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
