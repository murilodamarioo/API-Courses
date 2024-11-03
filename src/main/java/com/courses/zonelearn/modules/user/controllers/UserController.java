package com.courses.zonelearn.modules.user.controllers;

import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.usecases.CreateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        var response = this.createUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
