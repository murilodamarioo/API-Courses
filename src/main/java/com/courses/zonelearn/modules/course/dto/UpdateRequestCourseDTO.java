package com.courses.zonelearn.modules.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateRequestCourseDTO {
    private String name;
    private String category;
}
