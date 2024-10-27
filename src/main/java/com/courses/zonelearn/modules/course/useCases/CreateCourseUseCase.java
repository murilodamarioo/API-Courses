package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository repository;

    public Course execute(Course courseRecords) {
        if (courseRecords.getName().length() < 10 || courseRecords.getName().length() > 100) {
            throw new FieldsException("The field name must be between 10 and 100 characters");
        }

        if (courseRecords.getCategory().length() < 10 || courseRecords.getCategory().length() > 100) {
            throw new FieldsException("The field category must be between 10 and 100 characters");
        }
        return this.repository.save(courseRecords);
    }
}
