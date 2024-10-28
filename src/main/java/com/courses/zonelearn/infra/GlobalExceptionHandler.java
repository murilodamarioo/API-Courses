package com.courses.zonelearn.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException exception) {
        var threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, "The route was not found. Check the URL");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}
