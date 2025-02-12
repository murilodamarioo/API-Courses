package com.courses.zonelearn.modules.course.dto;

import com.courses.zonelearn.modules.course.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToggleResponseDTO {

    @Schema(example = "Become a Java Developer")
    private String name;

    @Schema(example = "ACTIVE")
    private Status status;
}
