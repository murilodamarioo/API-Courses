package com.courses.zonelearn.modules.user.usecases;

import com.courses.zonelearn.exceptions.EmailOrPasswordInvalidException;
import com.courses.zonelearn.modules.user.dto.LoginDTO;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.enums.Role;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthUserUseCaseTest {

    @InjectMocks
    private AuthUserUseCase authUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("USE CASE - It should not be able to authenticate user with wrong e-mail")
    public void authUser_InvalidEmail_ThrowsException() {
        LoginDTO authRequest = new LoginDTO("WRONG_EMAIL", "WRONG_PASSWORD");

        assertThatThrownBy(() -> authUserUseCase.execute(authRequest))
                .isInstanceOf(EmailOrPasswordInvalidException.class)
                .hasMessage("E-mail/Password incorrect");
    }

    @Test
    @DisplayName("USE CASE - It should not be able to authenticate user with wrong password")
    public void authUser_InvalidPassword_ThrowsException() {
        var user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .password("123456")
                .build();

        userRepository.saveAndFlush(user);

        LoginDTO authRequest = new LoginDTO("john@email.com", "WRONG_PASSWORD");

        assertThatThrownBy(() -> authUserUseCase.execute(authRequest))
                .isInstanceOf(EmailOrPasswordInvalidException.class)
                .hasMessage("E-mail/Password incorrect");
    }

    @Test
    @DisplayName("USE CASE - It should be able to authenticate")
    public void authUser_ValidData_AuthenticateUserSuccessfully() {
        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .role(Role.TEACHER)
                .email("john@email.com")
                .password("123456")
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        var authRequest = new LoginDTO(user.getEmail(), user.getPassword());
        ReflectionTestUtils.setField(authUserUseCase, "secretKey", "TEST@KEY");

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        var response = authUserUseCase.execute(authRequest);

        assertThat(response.getAccess_token()).isNotBlank();
        assertThat(response.getExpires_in()).isGreaterThan(Instant.now().toEpochMilli());
    }
}
