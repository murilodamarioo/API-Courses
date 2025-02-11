package com.courses.zonelearn.modules.user.controllers;

import com.courses.zonelearn.exceptions.EmailOrPasswordInvalidException;
import com.courses.zonelearn.modules.user.dto.AuthUserResponseDTO;
import com.courses.zonelearn.modules.user.dto.ErrorMessageDTO;
import com.courses.zonelearn.modules.user.dto.LoginDTO;
import com.courses.zonelearn.modules.user.usecases.AuthUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "User authentication")
public class AuthUserController {

    @Autowired
    AuthUserUseCase authUserUseCase;

    @PostMapping("/auth")
    @Operation(summary = "user auth", description = "This route is responsible for authenticate a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = AuthUserResponseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "E-mail/Password incorrect", content = {
                    @Content(schema = @Schema(
                            implementation = ErrorMessageDTO.class
                    ))
            })
    })
    public ResponseEntity<AuthUserResponseDTO> create(@RequestBody LoginDTO loginDTO) {
        var token = this.authUserUseCase.execute(loginDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(token);
    }
}
