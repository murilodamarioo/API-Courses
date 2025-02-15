package com.courses.zonelearn.modules.course.usecases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.UnauthorizedAccessException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.useCases.DeleteCourseUseCase;
import com.courses.zonelearn.modules.user.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DeleteCourseUseCaseTest {

    @InjectMocks
    private DeleteCourseUseCase deleteCourseUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @DisplayName("It should not be able to delete a course with non existing id")
    public void deleteCourse_NonExistingId_ThrowsException() {
        UUID fakeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        assertThatThrownBy(() -> deleteCourseUseCase.execute(fakeId, userId))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessage("The Id entered does not exist");
    }

    @Test
    @DisplayName("It should not be able to delete a course with invalid user id")
    public void deleteCourse_InvalidUserId_ThrowsException() {
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

        UUID fakeUserId = UUID.randomUUID();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        assertThatThrownBy(() -> deleteCourseUseCase.execute(course.getId(), fakeUserId))
                .isInstanceOf(UnauthorizedAccessException.class)
                .hasMessage("You are not allowed to delete this course");
    }


    @Test
    @DisplayName("It should be able to delete a course")
    public void deleteCourse_ValidData_DeleteCourseSuccessfully() {
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

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        deleteCourseUseCase.execute(course.getId(), user.getId());

        verify(courseRepository, times(1)).deleteById(course.getId());
    }

}
