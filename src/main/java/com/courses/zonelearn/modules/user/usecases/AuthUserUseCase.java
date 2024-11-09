package com.courses.zonelearn.modules.user.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.courses.zonelearn.exceptions.EmailOrPasswordInvalidException;
import com.courses.zonelearn.modules.user.dto.LoginDTO;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthUserUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(LoginDTO loginDTO) {
        var user = this.repository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new EmailOrPasswordInvalidException("E-mail/Password incorrect")
        );

        var passwordMatches = this.passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new EmailOrPasswordInvalidException("E-mail/Password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // Generate token
        return JWT.create()
                .withIssuer("@zonelearn")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withClaim("role", user.getRole().name())
                .withSubject(user.getId())
                .sign(algorithm);
    }
}
