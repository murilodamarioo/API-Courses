package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.modules.course.dto.UpdateRequestCourseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

@Service
public class UpdateCourseUseCase {

    @Autowired
    private CourseRepository repository;

    public Course execute(UUID id, UpdateRequestCourseDTO request) {
        Course course = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException("Non-existent course"));

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

        return this.repository.save(course);
    }
}
