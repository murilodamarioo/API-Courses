package com.courses.zonelearn.modules.course.controllers;

import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.useCases.CreateCourseUseCase;
import com.courses.zonelearn.modules.course.useCases.GetCoursesUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @Autowired
    private GetCoursesUseCase getCoursesUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody Course courseRecords) {
        var response = this.createCourseUseCase.execute(courseRecords);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        var response = this.getCoursesUseCase.execute(name, category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
