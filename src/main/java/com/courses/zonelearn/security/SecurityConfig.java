package com.courses.zonelearn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable Cross-Site Request Forgery
        http.csrf(AbstractHttpConfigurer::disable)
                // With requestMatchers "/user", "/user/auth" and "/courses" routes does not need authentication
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/user/auth").permitAll()
                            .requestMatchers("/user").permitAll()
                            .requestMatchers(HttpMethod.GET, "/courses/list").permitAll()
                            .requestMatchers(HttpMethod.PATCH, "/courses/{id}/active").hasRole("TEACHER")
                            .requestMatchers(HttpMethod.POST, "/courses").hasRole("TEACHER")
                            .requestMatchers(HttpMethod.PUT, "/courses/{id}").hasRole("TEACHER")
                            .requestMatchers(HttpMethod.DELETE, "/courses/{id}").hasRole("TEACHER");
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
