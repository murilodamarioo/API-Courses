package com.courses.zonelearn.modules.course.repository;

import com.courses.zonelearn.modules.course.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByCategoryContainingIgnoreCase(String category);
    List<Course> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);
}
