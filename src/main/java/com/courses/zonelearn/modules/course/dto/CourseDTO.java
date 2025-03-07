package com.courses.zonelearn.modules.course.dto;

import com.courses.zonelearn.modules.course.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDTO {

    @Schema(example = "c0ee4f50-8d6f-4a45-bb16-6bc10b90a1f0")
    private UUID id;

    @Schema(example = "React.js Beginner")
    private String name;

    @Schema(example = "Web Development")
    private String category;

    @Schema(example = "ACTIVE")
    private Status status;

    private LocalDateTime updateAt;
}
