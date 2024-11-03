package com.courses.zonelearn.modules.user.usecases;

import com.courses.zonelearn.exceptions.UserFoundException;
import com.courses.zonelearn.modules.user.dto.RegisterDTO;
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

    public User execute(RegisterDTO registerDTO) {
        this.repository.findByEmail(registerDTO.getEmail())
                .ifPresent((user) -> {
                   throw new UserFoundException("E-mail linked to another user");
                });

        User user = new User();
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setRole(registerDTO.getRole());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        return this.repository.save(user);
    }
}
