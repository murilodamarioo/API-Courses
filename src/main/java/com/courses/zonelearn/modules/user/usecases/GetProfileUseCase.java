package com.courses.zonelearn.modules.user.usecases;

import com.courses.zonelearn.modules.user.dto.ProfileUserResponseDTO;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import com.courses.zonelearn.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetProfileUseCase {

    @Autowired
    private UserRepository repository;

    public ProfileUserResponseDTO execute(UUID userId) {
        User user = this.repository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("Cannot show profile")
        );

        ProfileUserResponseDTO responseDTO = new ProfileUserResponseDTO();
        responseDTO.setFullName(user.getFirstName() + " " + user.getLastName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());

        return responseDTO;
    }
}
