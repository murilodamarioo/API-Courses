package com.courses.zonelearn.modules.course.repository;

import com.courses.zonelearn.modules.course.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

}
