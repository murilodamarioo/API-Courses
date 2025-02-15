package com.courses.zonelearn.modules.course;

import com.courses.zonelearn.factory.CourseFactory;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.useCases.GetCoursesUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class GetCoursesUseCaseTest {

    @InjectMocks
    private GetCoursesUseCase getCoursesUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @DisplayName("It should be able to get courses without filters")
    public void getCourses_WithoutFilters_GetCoursesSuccessfully() {
        List<Course> mockCourses = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            mockCourses.add(CourseFactory.makeCourse());
        }

        when(courseRepository.findAll()).thenReturn(mockCourses);

        var response = getCoursesUseCase.execute(null, null);

        assertThat(response).hasSize(5);
    }

    @Test
    @DisplayName("It should be able to get courses by name filter")
    public void getCourses_ByNameFilter_GetCoursesSuccessfully() {
        List<Course> mockCourses = new ArrayList<>();

        String nameFilter = "Spanish";

        for (int i = 0; i < 5; i++) {
            mockCourses.add(CourseFactory.makeCourse(UUID.randomUUID(), "Spanish", "", Status.ACTIVE, LocalDateTime.now(ZoneId.systemDefault())));
        }

        for (int j = 0; j < 3; j++) {
            mockCourses.add(CourseFactory.makeCourse(UUID.randomUUID(), "English", "", Status.ACTIVE, LocalDateTime.now(ZoneId.systemDefault())));
        }


        when(courseRepository.findByNameContainingIgnoreCase(nameFilter))
                .thenReturn(mockCourses.stream()
                        .filter(course -> course.getName().toLowerCase().contains(nameFilter.toLowerCase()))
                        .toList());

        var response  = getCoursesUseCase.execute(nameFilter, null);

        assertThat(response).hasSize(5);
    }

    @Test
    @DisplayName("It should be able to get courses by category filter")
    public void getCourses_ByCategoryFilter_GetCoursesSuccessfully() {
        List<Course> mockCourses = new ArrayList<>();

        String categoryFilter = "WeB";

        for (int i = 0; i < 5; i++) {
            mockCourses.add(CourseFactory.makeCourse(UUID.randomUUID(), "Java", "Web development", Status.ACTIVE, LocalDateTime.now(ZoneId.systemDefault())));
        }

        for (int j = 0; j < 3; j++) {
            mockCourses.add(CourseFactory.makeCourse(UUID.randomUUID(), "Kotlin", "Mobile development", Status.ACTIVE, LocalDateTime.now(ZoneId.systemDefault())));
        }

        when(courseRepository.findByCategoryContainingIgnoreCase(categoryFilter))
                .thenReturn(mockCourses.stream()
                        .filter(course -> course.getCategory().toLowerCase().contains(categoryFilter.toLowerCase()))
                        .toList());

        var response = getCoursesUseCase.execute(null, categoryFilter);

        assertThat(response).hasSize(5);
    }

    @Test
    @DisplayName("It should be able to get courses by name and category filters")
    public void getCourses_ByNameAndCategoryFilters_GetCoursesSuccessfully() {
        List<Course> mockCourses = new ArrayList<>();

        String categoryFilter = "Mo";
        String nameFilter = "Kot";

        for (int i = 0; i < 5; i++) {
            mockCourses.add(CourseFactory.makeCourse(UUID.randomUUID(), "Java", "Web development", Status.ACTIVE, LocalDateTime.now(ZoneId.systemDefault())));
        }

        for (int j = 0; j < 3; j++) {
            mockCourses.add(CourseFactory.makeCourse(UUID.randomUUID(), "Kotlin", "Mobile development", Status.ACTIVE, LocalDateTime.now(ZoneId.systemDefault())));
        }

        when(courseRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(nameFilter, categoryFilter))
                .thenReturn(mockCourses.stream()
                        .filter(course ->
                                course.getCategory().toLowerCase().contains(categoryFilter.toLowerCase()) && course.getName().toLowerCase().contains(nameFilter.toLowerCase()))
                        .toList());

        var response = getCoursesUseCase.execute(nameFilter, categoryFilter);

        assertThat(response).hasSize(3);
    }
}
