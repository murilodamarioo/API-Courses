package com.courses.zonelearn.modules.course.usecases;

import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.useCases.CreateCourseUseCase;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.enums.Role;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateCourseUseCaseTest {

    @InjectMocks
    private CreateCourseUseCase createCourseUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("It should not be able to create course with less than 10 characters in the name")
    public void createCourse_NameWithLessThanTenCharacters_ThrowsException() {
        UUID userId = UUID.randomUUID();

        var courseRecords = Course.builder()
                .name("Spanish")
                .category("Language")
                .teacher("John Doe")
                .status(Status.ACTIVE)
                .build();

        assertThatThrownBy(() -> createCourseUseCase.execute(courseRecords, userId))
                .isInstanceOf(FieldsException.class)
                .hasMessage("The field name must be between 10 and 100 characters");

    }

    @Test
    @DisplayName("It should not be able to create a course with less than 10 characters in the category")
    public void createCourse_categoryWithLessThanTenCharacters_ThrowsException() {
        UUID userId = UUID.randomUUID();

        var courseRecords = Course.builder()
                .name("Create a chatbot with Python")
                .category("I")
                .teacher("John Doe")
                .status(Status.ACTIVE)
                .build();

        assertThatThrownBy(() -> createCourseUseCase.execute(courseRecords, userId))
                .isInstanceOf(FieldsException.class)
                .hasMessage("The field category must be between 2 and 100 characters");
    }

    @Test
    @DisplayName("It should not be able to create a course with invalid user id")
    public void createCourse_InvalidUserId_ThrowsException() {
        UUID userId = UUID.randomUUID();

        var courseRecords = Course.builder()
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .status(Status.ACTIVE)
                .build();

        assertThatThrownBy(() -> createCourseUseCase.execute(courseRecords, userId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("user not found");
    }

    @Test
    @DisplayName("It should be able to create a course with valid fields and user id")
    public void createCourse_ValidData_CreateCourseSuccessfully() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .role(Role.TEACHER)
                .email("john@email.com")
                .password("123456")
                .build();

        var course = Course.builder()
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .status(Status.ACTIVE)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        var response = createCourseUseCase.execute(course, user.getId());

        assertThat(response.getName()).isEqualTo(course.getName());
        assertThat(response.getMessage()).isEqualTo("Course created successfully");
    }

}
