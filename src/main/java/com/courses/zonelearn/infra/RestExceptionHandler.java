package com.courses.zonelearn.infra;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.exceptions.EmailOrPasswordInvalidException;
import com.courses.zonelearn.exceptions.FieldsException;
import com.courses.zonelearn.exceptions.UserFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldsException.class)
    private ResponseEntity<RestErrorMessage> requiredFieldsHandler(FieldsException exception) {
        var threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    private ResponseEntity<RestErrorMessage> courseNotFoundHandler(CourseNotFoundException exception) {
        var threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(TransactionSystemException.class)
    private ResponseEntity<RestErrorMessage> invalidContentField(TransactionSystemException exception) {
        var threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(UserFoundException.class)
    private ResponseEntity<RestErrorMessage> userFoundException(UserFoundException exception) {
        var threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(EmailOrPasswordInvalidException.class)
    private ResponseEntity<RestErrorMessage> emailOrPasswordInvalidException(EmailOrPasswordInvalidException exception) {
        var threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

}
