package com.courses.zonelearn.modules.course;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.entities.Subscription;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.repository.SubscriptionRepository;
import com.courses.zonelearn.modules.course.useCases.CreateSubscriptionUseCase;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.enums.Role;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateSubscriptionUseCaseTest {

    @InjectMocks
    private CreateSubscriptionUseCase createSubscriptionUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    private UUID userId;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        courseId = UUID.randomUUID();
    }

    @Test
    @DisplayName("It should not be able to subscribe with invalid user id")
    public void CreateSubscription_InvalidUserId_ThrowsException() {

        assertThatThrownBy(() -> createSubscriptionUseCase.execute(null, userId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("It should not be able to subscribe with non-existing course id")
    public void CreateSubscription_NonExistingCourseId_ThrowsException() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> createSubscriptionUseCase.execute(courseId, userId))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessage("The Id entered does not exist");
    }

    @Test
    @DisplayName("It should be able to subscribe in a course")
    public void CreateSubscription_ValidData_CreateSubscriptionSuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));

        Subscription subscription = Subscription.builder()
                .userId(userId)
                .courseId(courseId)
                .status(Status.ACTIVE)
                .build();

        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        var response = createSubscriptionUseCase.execute(courseId, userId);

        assertNotNull(response);
        assertEquals(courseId, response.getCourseId());
        assertEquals(Status.ACTIVE, response.getStatus());
        verify(userRepository, times(1)).findById(userId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }
}
