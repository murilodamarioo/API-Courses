package com.courses.zonelearn.modules.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateCourseRequestDTO {

    @Schema(example = "Make a Spotify clone with React Native")
    private String name;

    @Schema(example = "Mobile Development")
    private String category;
}
