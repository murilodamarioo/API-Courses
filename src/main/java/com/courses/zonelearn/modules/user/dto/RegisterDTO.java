package com.courses.zonelearn.modules.user.dto;

import com.courses.zonelearn.modules.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {

    private String email;
    private String password;
    private Role role;
}
