package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.UnauthorizedAccessException;
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

    public Course execute(UUID courseId, String sub) {
        Course course = this.repository.findById(courseId).orElseThrow(() -> new CourseNotFoundException("Non-existent course") );

        String token = sub.replace("Bearer ", "").trim();
        String userId = jwtProvider.getSubFromJwt(token);
        UUID id = UUID.fromString(userId);

        if (!course.getCreatedBy().getId().equals(id)) {
            throw new UnauthorizedAccessException("User not authorized to toggle this course");
        }

        course.setStatus(course.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);

        return this.repository.save(course);
    }
}
