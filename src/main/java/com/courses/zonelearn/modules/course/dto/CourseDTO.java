package com.courses.zonelearn.modules.course.dto;

import com.courses.zonelearn.modules.course.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CourseDTO {
    private UUID id;
    private String name;
    private String category;
    private Status status;
    private LocalDateTime updateAt;
}
