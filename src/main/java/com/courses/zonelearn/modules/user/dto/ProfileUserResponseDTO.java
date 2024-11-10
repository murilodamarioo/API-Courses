package com.courses.zonelearn.modules.user.dto;

import com.courses.zonelearn.modules.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUserResponseDTO {

    private String fullName;
    private String email;
    private Role role;
}
