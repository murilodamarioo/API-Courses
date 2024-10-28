package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.modules.course.dto.CourseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCoursesUseCase {

    @Autowired
    private CourseRepository repository;

    public List<CourseDTO> execute(String name, String category) {
        List<Course> courses;

        if ((name == null || name.isEmpty()) && (category == null || category.isEmpty())) {
            courses = repository.findAll();
        } else if (category == null || category.isEmpty()) {
            courses = repository.findByNameContainingIgnoreCase(name);
        } else if (name == null || name.isEmpty()) {
            courses = repository.findByCategoryContainingIgnoreCase(category);
        } else {
            courses = repository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(name, category);
        }

        return courses.stream().map(course -> {
            return new CourseDTO(course.getId(), course.getName(), course.getCategory(), course.getStatus(), course.getUpdatedAt());
        }).collect(Collectors.toList());
    }
}
