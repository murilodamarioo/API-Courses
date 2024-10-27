package com.courses.zonelearn.modules.course.controllers;

import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.useCases.CreateCourseUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody Course courseRecords) {
        var response = this.createCourseUseCase.execute(courseRecords);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
