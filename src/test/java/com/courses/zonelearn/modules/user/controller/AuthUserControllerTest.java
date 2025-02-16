package com.courses.zonelearn.modules.user.controller;

import com.courses.zonelearn.exceptions.EmailOrPasswordInvalidException;
import com.courses.zonelearn.modules.user.dto.LoginDTO;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.enums.Role;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import com.courses.zonelearn.modules.user.usecases.AuthUserUseCase;
import com.courses.zonelearn.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthUserControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthUserUseCase authUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("CONTROLLER - It should not be able to authenticate with invalid e-mail")
    public void authUserController_InvalidEmail_ThrowsException() throws Exception {
        var authRequestDTO = LoginDTO.builder().email("matt@email.com").password("123456").build();

        when(authUserUseCase.execute(authRequestDTO)).thenThrow(new EmailOrPasswordInvalidException("E-mail/Password incorrect"));

        mvc.perform(MockMvcRequestBuilders.post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("E-mail/Password incorrect"));
    }

    @Test
    @DisplayName("CONTROLLER - It should not be able to authenticate with invalid password")
    public void authUserController_InvalidPassword_ThrowsException() throws Exception {
        var authRequestDTO = LoginDTO.builder().email("matt@email.com").password("INVALID_PASSWORD").build();

        when(authUserUseCase.execute(any(LoginDTO.class))).thenThrow(new EmailOrPasswordInvalidException("E-mail/Password incorrect"));

        mvc.perform(MockMvcRequestBuilders.post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("E-mail/Password incorrect"));
    }

    @Test
    @DisplayName("CONTROLLER - It should be able to authenticate user")
    public void authUserController_ValidData_AuthUserSuccessfully() throws Exception {
        var authRequestDTO = LoginDTO.builder().email("matt@email").password("VALID_PASSWORD").build();

        mvc.perform(MockMvcRequestBuilders.post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
