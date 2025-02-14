package com.courses.zonelearn.modules.user.usecases;

import com.courses.zonelearn.modules.user.entities.User;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class GetProfileUseCaseTest {

    @InjectMocks
    private GetProfileUseCase getProfileUseCase;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("It should not be able to get user profile data")
    public void getProfile_InvalidUserId_ThrowsException() {
        UUID fakeId = UUID.randomUUID();

        assertThatThrownBy(() -> getProfileUseCase.execute(fakeId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Cannot show profile");
    }

    @Test
    @DisplayName("It should be able to get user profile data")
    public void getProfile_ValidUserId_GetDataSuccessfully() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .password("123456")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var response = getProfileUseCase.execute(user.getId());

        assertThat(response.getFullName()).isEqualTo("John Doe");
        assertThat(response.getEmail()).isEqualTo("john@email.com");
    }
}
