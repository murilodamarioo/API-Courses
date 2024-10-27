package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCourseUseCase {

    @Autowired
    private CourseRepository repository;

    public void execute(UUID id) {
        this.repository.deleteById(id);
    }
}
