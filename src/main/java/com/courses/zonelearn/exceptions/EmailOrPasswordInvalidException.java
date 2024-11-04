package com.courses.zonelearn.exceptions;

public class EmailOrPasswordInvalidException extends RuntimeException {
    public EmailOrPasswordInvalidException(String message) {
        super(message);
    }
}
