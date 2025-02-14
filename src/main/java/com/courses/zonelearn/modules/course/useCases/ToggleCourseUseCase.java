package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.UnauthorizedAccessException;
import com.courses.zonelearn.modules.course.dto.ToggleResponseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ToggleCourseUseCase {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private JWTProvider jwtProvider;

    public ToggleResponseDTO execute(UUID courseId, UUID userId) {
        Course course = this.repository.findById(courseId).orElseThrow(() -> new CourseNotFoundException("Non-existent course") );

        if (!course.getCreatedBy().getId().equals(userId)) {
            throw new UnauthorizedAccessException("User not authorized to toggle this course");
        }

        course.setStatus(course.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);

        this.repository.save(course);

        return ToggleResponseDTO.builder()
                .name(course.getName())
                .status(course.getStatus())
                .build();
    }
}
