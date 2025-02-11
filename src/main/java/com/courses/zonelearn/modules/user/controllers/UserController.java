package com.courses.zonelearn.modules.user.controllers;

import com.courses.zonelearn.modules.user.dto.ProfileUserResponseDTO;
import com.courses.zonelearn.modules.user.entities.User;
import com.courses.zonelearn.modules.user.usecases.CreateUserUseCase;
import com.courses.zonelearn.modules.user.usecases.GetProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "User", description = "Information about user")
    @Operation(summary = "Show information about the user", description = "This route is responsible to show information about the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = ProfileUserResponseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "Cannot show profile")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ProfileUserResponseDTO> get(@RequestHeader("Authorization") String sub) {
        var response = this.getProfileUseCase.execute(sub);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
