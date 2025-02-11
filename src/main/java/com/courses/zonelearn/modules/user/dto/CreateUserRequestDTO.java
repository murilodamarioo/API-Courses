package com.courses.zonelearn.modules.user.dto;

import com.courses.zonelearn.modules.user.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    @Schema(example = "Maria")
    private String firstName;

    @Schema(example = "Doe")
    private String lastName;

    @Schema(example = "maria@email.com")
    private String email;

    @Schema(example = "123456")
    private String password;

    @Schema(example = "STUDENT")
    private Role role;
}
