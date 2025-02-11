package com.courses.zonelearn.modules.user.dto;

import com.courses.zonelearn.modules.user.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUserResponseDTO {

    @Schema(example = "John Doe")
    private String fullName;

    @Schema(example = "john@email.com")
    private String email;

    @Schema(example = "TEACHER")
    private Role role;
}
