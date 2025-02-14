package com.courses.zonelearn.modules.course.controllers;

import com.courses.zonelearn.modules.course.dto.*;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.useCases.*;
import com.courses.zonelearn.modules.user.dto.ErrorMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CreateCourseResponseDTO> create(@Valid @RequestBody CreateCourseRequestDTO courseRecords, HttpServletRequest request) {
        var courseEntity = Course.builder()
                .name(courseRecords.getName())
                .category(courseRecords.getCategory())
                .status(Status.ACTIVE)
                .build();

        var userId = request.getAttribute("user_id");

        var response = this.createCourseUseCase.execute(courseEntity, UUID.fromString(userId.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    @Operation(summary = "List all registered courses", description = "This route is responsible for show information about the courses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
                    )
            })
    })
    public ResponseEntity<List<CourseDTO>> get(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        var response = this.getCoursesUseCase.execute(name, category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a specific course", description = "This route delete a specific course by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "The Id entered does not exist", content = {
                    @Content(schema = @Schema(
                            implementation = ErrorMessageDTO.class
                    ))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Void> delete(@PathVariable UUID id, HttpServletRequest request) {
        var userId = request.getAttribute("user_id");

        this.deleteCourseUseCase.execute(id, UUID.fromString(userId.toString()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course", description = "this route is responsible for update a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = CourseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "The field 'name' cannot be empty or contain only spaces.", content = {
                    @Content(schema = @Schema(
                            implementation = ErrorMessageDTO.class
                    ))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CourseDTO> put(@PathVariable UUID id, @RequestBody UpdateRequestCourseDTO updateRequestCourseDTO, HttpServletRequest request) {
        var userId = request.getAttribute("user_id");

        var response = this.updateCourseUseCase.execute(id, updateRequestCourseDTO, UUID.fromString(userId.toString()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Toggle the status course", description = "this route is responsible for toggle status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = ToggleResponseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "Non-existent course", content = {
                    @Content(schema = @Schema(
                            implementation = ErrorMessageDTO.class
                    ))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ToggleResponseDTO> patch(@PathVariable UUID id, HttpServletRequest request) {
        var userId = request.getAttribute("user_id");

        var response = this.toggleCourseUseCase.execute(id, UUID.fromString(userId.toString()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
