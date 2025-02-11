package com.courses.zonelearn.modules.user.usecases;

import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.exceptions.UserFoundException;
import com.courses.zonelearn.modules.user.dto.CreateUserResponseDTO;
import com.courses.zonelearn.modules.user.dto.ErrorMessageDTO;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateUserResponseDTO execute(User register) {

        if (register.getFirstName().isEmpty()) {
            throw new FieldsException("First name is required");
        }
        if (register.getPassword().length() < 6) {
            throw new FieldsException("Password must be at least 6 characters long");
        }

        this.repository.findByEmail(register.getEmail())
                .ifPresent((user) -> {
                   throw new UserFoundException("E-mail linked to another user");
                });

        var password = passwordEncoder.encode(register.getPassword());
        register.setPassword(password);

        this.repository.save(register);

        return CreateUserResponseDTO.builder().message("User created successfully").build();
    }
}
