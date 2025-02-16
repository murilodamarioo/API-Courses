package com.courses.zonelearn.modules.user.controller;


import com.courses.zonelearn.exceptions.UserFoundException;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.enums.Role;
import com.courses.zonelearn.modules.user.usecases.CreateUserUseCase;
import com.courses.zonelearn.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class CreateUserControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("CONTROLLER - It should not be able to create user with first name empty")
    public void createUserController_FirstNameEmpty_ThrowsException() throws Exception {
        var user = User.builder().firstName("").lastName("Doe").email("john@email").password("123456").role(Role.TEACHER).build();

        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(user))
                        .header("Authorization", TestUtils.generateToken(UUID.fromString(UUID.randomUUID().toString()), "TEST@KEY")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("First name is required"));
    }

    @Test
    @DisplayName("CONTROLLER - It should not be able to create user with short password")
    public void createUserController_ShortPassword_ThrowsException() throws Exception {
        var user = User.builder().firstName("John").lastName("Doe").email("john@email.com").password("1234").build();

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(user))
                .header("Authorization", TestUtils.generateToken(UUID.fromString(UUID.randomUUID().toString()), "TEST@KEY")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password must be at least 6 characters long"));
    }

    @Test
    @DisplayName("CONTROLLER - It should not be able to create with existing user email")
    public void createUserController_ExistingUserEmail_ThrowsException() throws Exception {
        var user = User.builder().firstName("John").lastName("Doe").email("john@email.com").password("123456").build();

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(user))
                .header("Authorization", TestUtils.generateToken(UUID.fromString(UUID.randomUUID().toString()), "TEST@KEY")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User created successfully"));

        var newUser = User.builder().firstName("John").lastName("Mason").email("john@email.com").password("123456").build();

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(newUser))
                .header("Authorization", TestUtils.generateToken(UUID.fromString(UUID.randomUUID().toString()), "TEST@KEY")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("E-mail linked to another user"));
    }

    @Test
    @DisplayName("CONTROLLER - It should be able to create a user")
    public void createUserController_ValidData_CreateUserSuccessfully() throws Exception {
        var user = User.builder().firstName("Maria").lastName("Doe").email("maria@email.com").password("123456").build();

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(user))
                .header("Authorization", TestUtils.generateToken(UUID.fromString(UUID.randomUUID().toString()), "TEST@KEY")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User created successfully"));
    }


}
