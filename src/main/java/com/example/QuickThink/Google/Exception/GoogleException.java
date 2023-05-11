package com.example.QuickThink.Google.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GoogleException {
    @ExceptionHandler(InvalidRedirect.class)
    public ResponseEntity<String> redirectException(InvalidRedirect err) {
        return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
