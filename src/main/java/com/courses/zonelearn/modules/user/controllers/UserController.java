package com.courses.zonelearn.modules.user.controllers;

import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.usecases.CreateUserUseCase;
import com.courses.zonelearn.modules.user.usecases.GetProfileUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private GetProfileUseCase getProfileUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        var response = this.createUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> get(@RequestHeader("Authorization") String sub) {
        var response = this.getProfileUseCase.execute(sub);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
