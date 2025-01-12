package com.courses.zonelearn.modules.course.controllers;

import com.courses.zonelearn.modules.course.dto.SubscriptionResponseDTO;
import com.courses.zonelearn.modules.course.useCases.CreateSubscriptionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("subscription")
public class SubscriptionController {

    @Autowired
    private CreateSubscriptionUseCase createSubscriptionUseCase;

    @PostMapping("/course/{id}")
    public ResponseEntity<SubscriptionResponseDTO> create(@PathVariable UUID id, @RequestHeader("Authorization") String sub) {
        var response = this.createSubscriptionUseCase.execute(id, sub);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
