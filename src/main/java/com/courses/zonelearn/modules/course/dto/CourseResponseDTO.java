package com.courses.zonelearn.modules.course.dto;

import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponseDTO {

    private UUID id;
    private String name;
    private UserDTO createdBy;
    private String teacher;
    private String category;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
