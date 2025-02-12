package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.UnauthorizedAccessException;
import com.courses.zonelearn.modules.course.dto.CourseDTO;
import com.courses.zonelearn.modules.course.dto.UpdateRequestCourseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

@Service
public class UpdateCourseUseCase {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private JWTProvider jwtProvider;

    public CourseDTO execute(UUID id, UpdateRequestCourseDTO request, String sub) {
        Course course = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException("Non-existent course"));

        String token = sub.replace("Bearer ", "");
        String idFromToken = jwtProvider.getSubFromJwt(token);
        UUID userId = UUID.fromString(idFromToken);

        if (!course.getCreatedBy().getId().equals(userId)) throw new UnauthorizedAccessException("You are not allowed to edit this course");

        if (request.getName() == null && request.getCategory() == null) {
            throw new IllegalArgumentException("At least one field (name or category) must be provided for update.");
        }

        if (request.getName() != null) {
            String trimmedName = request.getName().trim();
            if (trimmedName.isEmpty()) {
                throw new TransactionSystemException("The field 'name' cannot be empty or contain only spaces.");
            }
            course.setName(trimmedName);
        }

        if (request.getCategory() != null) {
            String trimmedCategory = request.getCategory().trim();
            if (trimmedCategory.isEmpty()) {
                throw new TransactionSystemException("The field 'category' cannot be empty or contain only spaces.");
            }
            course.setCategory(trimmedCategory);
        }

        this.repository.save(course);

        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .category(course.getCategory())
                .status(course.getStatus())
                .updateAt(course.getUpdatedAt())
                .build();
    }
}
