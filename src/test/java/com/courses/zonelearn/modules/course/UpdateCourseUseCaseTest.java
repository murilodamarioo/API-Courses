package com.courses.zonelearn.modules.course;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.UnauthorizedAccessException;
import com.courses.zonelearn.modules.course.dto.UpdateRequestCourseDTO;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.useCases.UpdateCourseUseCase;
import com.courses.zonelearn.modules.user.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UpdateCourseUseCaseTest {

    @InjectMocks
    private UpdateCourseUseCase updateCourseUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @DisplayName("It should not be able to update with non-existing course id")
    public void updateCourse_NonExistingCourseId_ThrowsException() {
        UUID fakeCourseId = UUID.randomUUID();

        assertThatThrownBy(() -> updateCourseUseCase.execute(fakeCourseId, null, null))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessage("Non-existent course");
    }

    @Test
    @DisplayName("It should not be able to update with invalid user id")
    public void updateCourse_InvalidUserId_ThrowsException() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        var course = Course.builder()
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .createdBy(user)
                .status(Status.ACTIVE)
                .build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        UUID fakerUserId = UUID.randomUUID();

        assertThatThrownBy(() -> updateCourseUseCase.execute(course.getId(), null, fakerUserId))
                .isInstanceOf(UnauthorizedAccessException.class)
                .hasMessage("You are not allowed to edit this course");
    }

    @Test
    @DisplayName("It should not be able to update a course when no fields are provided")
    public void updateCourse_FieldsNotProvided_ThrowsException() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        var course = Course.builder()
                .id(UUID.randomUUID())
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .createdBy(user)
                .status(Status.ACTIVE)
                .build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        UpdateRequestCourseDTO request = UpdateRequestCourseDTO.builder().build();

        assertThatThrownBy(() -> updateCourseUseCase.execute(course.getId(), request, user.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("At least one field (name or category) must be provided for update.");
    }


    @Test
    @DisplayName("It should not be able to update a course with blank name ")
    public void updateCourse_FieldNameBlank_ThrowsException() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        var course = Course.builder()
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .createdBy(user)
                .status(Status.ACTIVE)
                .build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        UpdateRequestCourseDTO request = UpdateRequestCourseDTO.builder()
                .name("")
                .category("IA")
                .build();

        assertThatThrownBy(() -> updateCourseUseCase.execute(course.getId(), request, user.getId()))
                .isInstanceOf(TransactionSystemException.class)
                .hasMessage("The field 'name' cannot be empty or contain only spaces.");
    }

    @Test
    @DisplayName("It should not be able to update a course with blank category")
    public void updateCourse_FieldCategoryBlank_ThrowsException() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        var course = Course.builder()
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .createdBy(user)
                .status(Status.ACTIVE)
                .build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        UpdateRequestCourseDTO request = UpdateRequestCourseDTO.builder()
                .name("Create a chatbot with IA and Python")
                .category("")
                .build();

        assertThatThrownBy(() -> updateCourseUseCase.execute(course.getId(), request, user.getId()))
                .isInstanceOf(TransactionSystemException.class)
                .hasMessage("The field 'category' cannot be empty or contain only spaces.");
    }

    @Test
    @DisplayName("It should be able to update a course")
    public void updateCourse_ValidData_UpdateCourseSuccessfully() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        var course = Course.builder()
                .name("Create a chatbot with Python")
                .category("IA")
                .teacher("John Doe")
                .createdBy(user)
                .status(Status.ACTIVE)
                .build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        UpdateRequestCourseDTO request = UpdateRequestCourseDTO.builder()
                .name("Create a chatbot with IA and Python")
                .category("Python")
                .build();

        var response = updateCourseUseCase.execute(course.getId(), request, user.getId());

        assertThat(response)
                .hasFieldOrPropertyWithValue("name", "Create a chatbot with IA and Python")
                .hasFieldOrPropertyWithValue("category", "Python");

        verify(courseRepository, times(1)).save(course);
    }
}
