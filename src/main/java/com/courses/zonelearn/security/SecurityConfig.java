package com.courses.zonelearn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable Cross-Site Request Forgery
        http.csrf(AbstractHttpConfigurer::disable)
                // With requestMatchers "/user", "/user/auth" and "/courses" routes does not need authentication
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/user/auth").permitAll()
                            .requestMatchers("/user").permitAll()
                            .requestMatchers(HttpMethod.GET,"/courses").permitAll();
                    auth.anyRequest().authenticated();
                });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
