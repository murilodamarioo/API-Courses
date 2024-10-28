package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteCourseUseCase {

    @Autowired
    private CourseRepository repository;

    public void execute(UUID id) {
        Optional<Course> courseExists = this.repository.findById(id);

        if (courseExists.isEmpty()) {
            throw new CourseNotFoundException("The Id entered does not exist");
        }

        this.repository.deleteById(id);
    }
}
