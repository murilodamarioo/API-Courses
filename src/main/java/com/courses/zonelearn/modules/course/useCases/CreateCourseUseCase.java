package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.modules.course.dto.CourseResponseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.user.dto.UserDTO;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import com.courses.zonelearn.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTProvider jwtProvider;

    public CourseResponseDTO execute(Course courseRecords, String sub) {

        if (courseRecords.getName().length() < 10 || courseRecords.getName().length() > 100) {
            throw new FieldsException("The field name must be between 10 and 100 characters");
        }

        if (courseRecords.getCategory().length() < 2 || courseRecords.getCategory().length() > 100) {
            throw new FieldsException("The field category must be between 10 and 100 characters");
        }

        String token = sub.replace("Bearer ", "").trim();
        String userId = this.jwtProvider.getSubFromJwt(token);
        UUID id = UUID.fromString(userId);

        User user = this.userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );

        // Set the course creator
        courseRecords.setCreatedBy(user);
        courseRecords.setTeacher(user.getFirstName() + " " + user.getLastName());

        Course savedCourse = this.courseRepository.save(courseRecords);

        return mapToResponseDTO(savedCourse);
    }

    private CourseResponseDTO mapToResponseDTO(Course courseRecords) {
        // Map course to course response dto
        CourseResponseDTO courseDTO = new CourseResponseDTO();
        courseDTO.setId(courseRecords.getId());
        courseDTO.setName(courseRecords.getName());
        courseDTO.setTeacher(courseRecords.getTeacher());
        courseDTO.setCategory(courseRecords.getCategory());
        courseDTO.setStatus(courseRecords.getStatus());
        courseDTO.setCreatedAt(courseRecords.getCreatedAt());
        courseDTO.setUpdatedAt(courseRecords.getUpdatedAt());

        // Map user to user dto
        UserDTO userDTO = new UserDTO();
        userDTO.setId(courseRecords.getCreatedBy().getId());
        userDTO.setFullName(courseRecords.getCreatedBy().getFirstName() + " " + courseRecords.getCreatedBy().getLastName());
        courseDTO.setCreatedBy(userDTO);

        return courseDTO;
    }
}


