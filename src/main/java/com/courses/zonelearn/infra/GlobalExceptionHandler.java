package com.courses.zonelearn.infra;

import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.exceptions.UserFoundException;
import com.courses.zonelearn.modules.user.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException exception) {
        var threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, "The route was not found. Check the URL");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationExceptions(MethodArgumentNotValidException exception) {
        StringBuilder errorMessages = new StringBuilder("Validation error(s)- ");
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessages.append(String.format("%s: %s; ", error.getField(), error.getDefaultMessage()));
        });

        var threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, errorMessages.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(FieldsException.class)
    public ResponseEntity<ErrorMessageDTO> handleFieldsException(FieldsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserFoundException(UserFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(ex.getMessage()));
    }
}
