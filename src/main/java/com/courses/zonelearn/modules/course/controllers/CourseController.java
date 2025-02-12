package com.courses.zonelearn.modules.course.controllers;

import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.modules.course.dto.CreateCourseRequestDTO;
import com.courses.zonelearn.modules.course.dto.CreateCourseResponseDTO;
import com.courses.zonelearn.modules.course.dto.UpdateRequestCourseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.useCases.*;
import com.courses.zonelearn.modules.user.dto.ErrorMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course", description = "Course routes")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @Autowired
    private GetCoursesUseCase getCoursesUseCase;

    @Autowired
    private DeleteCourseUseCase deleteCourseUseCase;

    @Autowired
    private UpdateCourseUseCase updateCourseUseCase;

    @Autowired
    private ToggleCourseUseCase toggleCourseUseCase;

    @PostMapping
    @Operation(summary = "Register a course", description = "This route is responsible to register a course")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(
                            implementation = CreateCourseResponseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "The field name must be between 10 and 100 characters", content = {
                    @Content(schema = @Schema(
                            implementation = ErrorMessageDTO.class
                    ))
            })
    })
    public ResponseEntity<CreateCourseResponseDTO> create(@Valid @RequestBody CreateCourseRequestDTO courseRecords, @RequestHeader("Authorization") String sub) {
        var courseEntity = Course.builder()
                .name(courseRecords.getName())
                .category(courseRecords.getCategory())
                .build();

        var response = this.createCourseUseCase.execute(courseEntity, sub);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> get(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        var response = this.getCoursesUseCase.execute(name, category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestHeader("Authorization") String sub) {
        this.deleteCourseUseCase.execute(id, sub);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable UUID id, @RequestBody UpdateRequestCourseDTO request, @RequestHeader("Authorization") String sub) {
        var response = this.updateCourseUseCase.execute(id, request, sub);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Course> patch(@PathVariable UUID id, @RequestHeader("Authorization") String sub) {
        var response = this.toggleCourseUseCase.execute(id, sub);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
