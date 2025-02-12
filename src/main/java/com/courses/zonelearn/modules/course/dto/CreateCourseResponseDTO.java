package com.courses.zonelearn.modules.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateCourseResponseDTO {

    @Schema(example = "Make a Netflix clone with React.js")
    private String name;

    @Schema(example = "John Doe")
    private String teacher;

    @Schema(example = "Web Development")
    private String category;

    @Schema(example = "Course created successfully")
    private String message;
}
