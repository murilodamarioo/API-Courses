package com.courses.zonelearn.modules.user.controllers;

import com.courses.zonelearn.modules.user.dto.LoginDTO;
import com.courses.zonelearn.modules.user.usecases.AuthUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("user")
public class AuthUserController {

    @Autowired
    AuthUserUseCase authUserUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody LoginDTO loginDTO) {
        var token = this.authUserUseCase.execute(loginDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(token);
    }
}
