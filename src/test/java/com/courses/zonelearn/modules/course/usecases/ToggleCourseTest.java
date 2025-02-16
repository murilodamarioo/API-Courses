package com.courses.zonelearn.modules.course.usecases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.UnauthorizedAccessException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.useCases.ToggleCourseUseCase;
import com.courses.zonelearn.modules.user.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ToggleCourseTest {

    @InjectMocks
    private ToggleCourseUseCase toggleCourseUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @DisplayName("USE CASE - It should not be able to toggle the course with non-existing id")
    public void toggleCourse_NonExistingId_ThrowsException() {
        UUID fakerCourseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        assertThatThrownBy(() -> toggleCourseUseCase.execute(fakerCourseId, userId))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessage("Non-existent course");
    }

    @Test
    @DisplayName("USE CASE - It should not be able to toggle the course with invalid user id")
    public void toggleCourse_InvalidUserId_ThrowsException() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        Course course = Course.builder()
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .createdBy(user)
                .status(Status.ACTIVE)
                .build();

        UUID fakerUserId = UUID.randomUUID();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        assertThatThrownBy(() -> toggleCourseUseCase.execute(course.getId(), fakerUserId))
                .isInstanceOf(UnauthorizedAccessException.class)
                .hasMessage("User not authorized to toggle this course");
    }

    @Test
    @DisplayName("USE CASE - It should be able to toggle a course from ACTIVE to INACTIVE")
    public void toggleCourse_ValidUserAndCourseId_ToggleCourseSuccessfully() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        Course course = Course.builder()
                .id(UUID.randomUUID())
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .createdBy(user)
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.now(ZoneId.systemDefault()))
                .build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        var response = toggleCourseUseCase.execute(course.getId(), user.getId());

        assertThat(response).
                hasFieldOrPropertyWithValue("name", course.getName())
                .hasFieldOrPropertyWithValue("status", course.getStatus());

        verify(courseRepository, times(1)).save(course);
    }
}
