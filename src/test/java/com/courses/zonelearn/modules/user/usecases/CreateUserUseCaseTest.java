package com.courses.zonelearn.modules.user.usecases;

import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.exceptions.UserFoundException;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateUserUseCaseTest {

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("USE CASE - It should not be able to create user with first name empty")
    public void createUser_FirstNameEmpty_ThrowsException() {
        var user = User.builder()
                .firstName("")
                .lastName("Doe")
                .email("john@email.com")
                .password("123456")
                .build();

        try {
            createUserUseCase.execute(user);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(FieldsException.class);
            assertThat(e.getMessage()).isEqualTo("First name is required");
        }

    }

    @Test
    @DisplayName("USE CASE - It should not be able to create user with short password")
    public void createUser_ShortPassword_ThrowsException() {
        var user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .password("1234")
                .build();

        try {
            createUserUseCase.execute(user);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(FieldsException.class);
            assertThat(e.getMessage()).isEqualTo("Password must be at least 6 characters long");
        }
    }

    @Test
    @DisplayName("USE CASE - It should not be able to create user with existing user email")
    public void createUser_ExistingUserEmail_ThrowsException() {
        var user = User.builder()
                .firstName("Maria")
                .lastName("Doe")
                .email("maria@email.com")
                .password("123456")
                .build();

        userRepository.saveAndFlush(user);

        var newUser = User.builder()
                .firstName("Maria")
                .lastName("Jane")
                .email("maria@email.com")
                .password("123456")
                .build();

        try {
            createUserUseCase.execute(newUser);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserFoundException.class);
            assertThat(e.getMessage()).isEqualTo("E-mail linked to another user");
        }
    }

    @Test
    @DisplayName("USE CASE - It should be able to create user")
    public void createUser_ValidData_CreatesUserSuccessfully() {
        var user = User.builder()
                .firstName("John")
                .lastName("Smith")
                .email("john@email.com")
                .password("123456")
                .build();

        var response = createUserUseCase.execute(user);

        assertThat(response.getMessage()).isEqualTo("User created successfully");
    }
}
