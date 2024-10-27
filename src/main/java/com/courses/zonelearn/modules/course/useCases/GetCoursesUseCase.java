package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.modules.course.dto.CourseDTO;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCoursesUseCase {

    @Autowired
    private CourseRepository repository;

    public List<CourseDTO> execute() {
        var courses = this.repository.findAll();

        return courses.stream().map(course -> {
            return new CourseDTO(course.getName(), course.getCategory(), course.getStatus(), course.getUpdatedAt());
        }).collect(Collectors.toList());
    }
}
