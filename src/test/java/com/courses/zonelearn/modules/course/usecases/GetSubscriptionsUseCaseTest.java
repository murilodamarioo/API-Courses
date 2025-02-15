package com.courses.zonelearn.modules.course.usecases;

import com.courses.zonelearn.modules.course.entities.Subscription;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.SubscriptionRepository;
import com.courses.zonelearn.modules.course.useCases.GetSubscriptionsUseCase;
import com.courses.zonelearn.modules.user.entities.User;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class GetSubscriptionsUseCaseTest {

    @InjectMocks
    private GetSubscriptionsUseCase getSubscriptionsUseCase;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    private UUID userId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("It should not be able to fetch subscriptions with invalid id")
    public void getSubscriptions_InvalidUserId_ThrowsException() {

        assertThatThrownBy(() -> getSubscriptionsUseCase.execute(userId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("It should be able to fetch subscriptions")
    public void getSubscriptions_ValidData_GetSubscriptionsSuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        List<Subscription> subscriptions = List.of(
                Subscription.builder().userId(userId).courseId(UUID.randomUUID()).status(Status.ACTIVE).build(),
                Subscription.builder().userId(userId).courseId(UUID.randomUUID()).status(Status.ACTIVE).build()
        );

        when(subscriptionRepository.findByUserId(userId)).thenReturn(subscriptions);

        var response = getSubscriptionsUseCase.execute(userId);

        assertNotNull(response);
        assertThat(response).hasSize(2);

        verify(userRepository, times(1)).findById(userId);
        verify(subscriptionRepository, times(1)).findByUserId(userId);
    }
}
