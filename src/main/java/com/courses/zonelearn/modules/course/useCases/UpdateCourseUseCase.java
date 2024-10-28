package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.modules.course.dto.UpdateRequestCourseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCourseUseCase {

    @Autowired
    private CourseRepository repository;

    public Course execute(UUID id, UpdateRequestCourseDTO request) {
        Course course = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException("Non-existent course"));

        if (request.getName() != null) {
            course.setName(request.getName());
        }

        if (request.getCategory() != null) {
            course.setCategory(request.getCategory());
        }

        return this.repository.save(course);
    }
}
