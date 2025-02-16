package com.courses.zonelearn.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @Schema(example = "john@email.com")
    private String email;

    @Schema(example = "123456")
    private String password;
}
