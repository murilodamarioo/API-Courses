package com.courses.zonelearn.modules.course.controllers;

import com.courses.zonelearn.modules.course.dto.GetSubscriptionsResponseDTO;
import com.courses.zonelearn.modules.course.dto.SubscriptionResponseDTO;
import com.courses.zonelearn.modules.course.entities.Subscription;
import com.courses.zonelearn.modules.course.useCases.CreateSubscriptionUseCase;
import com.courses.zonelearn.modules.course.useCases.GetSubscriptionsUseCase;
import com.courses.zonelearn.modules.user.dto.ErrorMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("subscription")
@Tag(name = "Subscription")
public class SubscriptionController {

    @Autowired
    private CreateSubscriptionUseCase createSubscriptionUseCase;

    @Autowired
    private GetSubscriptionsUseCase getSubscriptionsUseCase;

    @PostMapping("/course/{id}")
    @Operation(summary = "Register a subscription", description = "This route is responsible to register a subscription")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = SubscriptionResponseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "The Id entered does not exist", content = {
                    @Content(schema = @Schema(
                            implementation = ErrorMessageDTO.class
                    ))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<SubscriptionResponseDTO> create(@PathVariable UUID id, @RequestHeader("Authorization") String sub) {
        var response = this.createSubscriptionUseCase.execute(id, sub);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/my-courses")
    @Operation(summary = "List subscription", description = "Fetches all of a user's subscriptions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = Subscription.class
                    ))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<Subscription>> get(@RequestHeader("Authorization") String sub) {
        var response = this.getSubscriptionsUseCase.execute(sub);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
