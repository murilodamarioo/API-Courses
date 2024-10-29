package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ToggleCourseUseCase {

    @Autowired
    private CourseRepository repository;

    public Course execute(UUID id) {
        Course course = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException("Non-existent course") );

        if (course.getStatus() == Status.ACTIVE) {
            course.setStatus(Status.INACTIVE);
        } else {
            course.setStatus(Status.ACTIVE);
        }

        return this.repository.save(course);
    }
}
