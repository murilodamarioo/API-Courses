package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.UnauthorizedAccessException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCourseUseCase {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private JWTProvider jwtProvider;

    public void execute(UUID courseId, String sub) {
        Course course = this.repository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException("The Id entered does not exist")
        );

        String token = sub.replace("Bearer ", "").trim();
        String idFromToken = jwtProvider.getSubFromJwt(token);
        UUID userId = UUID.fromString(idFromToken);

        if (!course.getCreatedBy().getId().equals(userId)) throw new UnauthorizedAccessException("You are not allowed to delete this course");

        this.repository.deleteById(courseId);
    }
}
