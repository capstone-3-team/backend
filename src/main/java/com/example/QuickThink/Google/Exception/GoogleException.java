package com.example.QuickThink.Google.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @ExceptionHandler(InvalidRedirect.class)
    public ResponseEntity<String> redirectException() {
        return new ResponseEntity<new InvalidRedirect().getMessage(), >()
    }
}
